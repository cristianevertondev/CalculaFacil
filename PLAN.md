# CalculaFácil — Plano de Desenvolvimento

Documento de estado do projeto. Mantém o planejamento, o progresso e o histórico de
decisões para que o desenvolvimento completo seja rastreável.

## Visão do produto

Calculadora completa para situações do dia a dia, com interface moderna, bonita, rápida
e intuitiva. Deve transmitir sensação de qualidade profissional (pronto para Google Play),
não parecer um tutorial.

## Arquitetura

Estrutura de pacotes sob `com.cristian.calculafacil`:

```
├── MainActivity.kt            # Activity única, seta o tema + navegação
├── navigation/
│   └── AppNavigation.kt        # Grafo de navegação Compose
├── model/                      # Modelos de domínio (definições de calculadoras, categorias)
├── calculation/                # LÓGICA de cálculo — pura, testável, sem Android
│   ├── Percentage.kt
│   ├── Interest.kt
│   ├── ...                     # um arquivo por grupo de calculadoras
│   └── Format.kt               # helpers de formatação (BRL, número)
├── ui/
│   ├── theme/                  # Tema próprio (cores, tipografia, shapes)
│   ├── components/             # Componentes reutilizáveis (cards, campos, resultados)
│   ├── screens/                # Telas
│   │   ├── home/               # Home + categorias
│   │   └── calculator/         # Tela genérica de calculadora
│   └── calculator/             # Modelos de UI/ViewModel das calculadoras
├── viewmodel/                  # ViewModels
└── res/                        # Recursos (strings, temas, ícones)
```

Princípios:
- A lógica matemática NÃO depende da UI → testável via JVM.
- Navegação via `androidx.navigation.compose`.
- ViewModel para estado de telas quando fizer sentido.
- Formatação BRL via `java.util.Locale`/`NumberFormat`.
- Máximo de strings em `strings.xml`.
- Sem bibliotecas desnecessárias; apenas APIs oficiais.

## Identidade visual

- Paleta própria (verde/teal financeiro, contrastes bons para dark/light).
- Tipografia: FontFamily padrão com pesos ajustados + possibilidade de fonte custom.
- Shapes arredondados; cards com elevação sutil; gradientes nos destaques.
- Animações leves: transições de tela, aparecimento de resultados, feedback de botões.
  Sem animações pesadas para preservar desempenho em aparelhos simples.

## Funcionalidades (calculadoras)

### Financeiras
- [ ] Porcentagem
- [ ] Desconto
- [ ] Aumento
- [ ] Regra de três
- [ ] Juros simples
- [ ] Juros compostos
- [ ] Parcelamento
- [ ] Financiamento (Price)
- [ ] Financiamento (SAC) — adicional BR
- [ ] Rendimento de poupança — adicional BR (opcional)

### Trabalhistas
- [ ] Salário (hora/dia/mês)
- [ ] Hora extra (percentuais)
- [ ] Férias (1/3 constitucional)
- [ ] 13º salário
- [ ] Rescisão (básico)

### Saúde
- [ ] IMC
- [ ] Calorias/metabolismo (Harris-Benedict)

### Veículo e cotidiano
- [ ] Combustível (custo por km / autonomia)
- [ ] Consumo do veículo (km/l)
- [ ] Divisão de conta

### Conversores
- [ ] Conversor de unidades (comprimento, massa, temperatura, etc.)
- [ ] Conversor de moedas — arquitetura com interface de fonte de taxas
      preparada para atualização futura (sem rede agora)

### Educação
- [ ] Média escolar (aritmética/ponderada)
- [ ] Nota necessária para aprovação
- [ ] Datas/idade (entre datas, idade, dias úteis)

## Etapas de desenvolvimento

Cada etapa termina com build + testes passando.

1. **Fundação** — refatorar MainActivity, criar tema próprio, navegação, Home com
   categorias/cards, componentes base, estrutura de pacotes. ✅/⬜
2. **Financeiras I** — Porcentagem, Desconto, Aumento, Regra de três, Juros simples,
   Juros compostos (+ lógica e testes).
3. **Financeiras II** — Parcelamento, Financiamento (Price/SAC).
4. **Trabalhistas** — Salário, Hora extra, Férias, 13º, Rescisão.
5. **Saúde** — IMC, Calorias.
6. **Veículo/cotidiano** — Combustível, Consumo, Divisão de conta.
7. **Conversores** — Unidades, Moedas.
8. **Educação** — Média, Nota, Datas/idade.
9. **Auditoria final** — testes instrumentados, revisão completa, correção de bugs,
   build de release, verificação da lista de critérios.

## Critério de conclusão

- Todas as calculadoras planejadas funcionando.
- Interface profissional; animações suaves; navegação ok.
- Cálculos testados (unitários por calculadora).
- Erros tratados; modo claro/escuro ok.
- Projeto compila sem erros; testes passam.
- Auditoria final realizada e bugs corrigidos.

## Histórico

- **2026-09-02** — Estado inicial: app único com calculadora de porcentagem no
  `MainActivity.kt`. Build funciona. Início da Fase 1.

## Estado do projeto (2026-09-02 — auditoria concluída)

**Fases 1–9 concluídas.** Todas as 20 ferramentas implementadas e testadas:

- Financeiras (8): Porcentagem, Desconto, Aumento, Regra de três, Juros simples,
  Juros compostos, Parcelamento, Financiamento (Price + SAC).
- Trabalhistas (5): Salário, Hora extra, Férias, 13º, Rescisão.
- Saúde (2): IMC, Calorias (Mifflin-St Jeor).
- Veículo/cotidiano (3): Combustível, Consumo, Divisão de conta.
- Conversores (2): Unidades (4 dimensões), Moedas (arquitetura `CurrencyRateSource`
  preparada para atualização de taxas por rede).
- Educação (3): Média escolar, Nota para aprovação, Idade.

**Arquitetura:** pacotes `model`, `calculation` (lógica pura), `ui` (theme,
components, screens), `viewmodel`, `navigation`. Tela de calculadora genérica
dirigida por dados; conversores com tela própria.

**Qualidade:**
- 177 testes unitários + 3 instrumentados (Compose UI), todos passando.
- Lint sem erros (apenas avisos informativos de versões mais novas).
- Build release com R8/shrink: APK de ~1,5 MB (de ~19,6 MB debug).
- Modo claro/escuro, edge-to-edge, acessibilidade básica, estado preservado
  (ViewModel / rememberSaveable).

**Correções feitas na auditoria:** desugaring de `java.time` (minSdk 24),
detecção de dark theme via `isSystemInDarkTheme`, strings de taxa sem `%` cru
(lint), guarda de overflow em engines financeiras, insets de barra de sistema na
Home, remoção de código morto e strings/recursos não usados, remoção de testes
placeholder, labels redundantes no manifesto.

**Melhorias futuras sugeridas:** fontes customizadas, testes de acessibilidade,
sufixos de resultado (unidades) movidos para recursos, integração de taxas de
câmbio em tempo real, atualização de AGP/Gradle/Kotlin.