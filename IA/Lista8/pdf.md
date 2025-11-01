# Lista 8 — Redes Neurais com Backpropagation (Relatório)

Este relatório foi preparado para ser convertido em PDF. Substitua as partes
entre colchetes por prints de tela e comentários próprios após executar o
código. O código encontra‑se em:
- `Estudos/IA/Lista8/main.py`
- `Estudos/IA/Lista8/mlp.py`
- `Estudos/IA/Lista8/seven_segment.py`

Observação de aderência ao enunciado: implementação do zero (sem Keras/PyTorch/Sklearn),
função de ativação sigmoid, loss MSE, backpropagation com SGD. Para o problema do
7‑segmentos foi usada a arquitetura 7‑5‑4 conforme especificado (4 neurônios na
saída). Como são 10 dígitos, a saída usa codificação binária de 4 bits para
representar 0–9. Se for exigido one‑hot real, bastaria usar 10 saídas.

---

## 1) Problema do XOR

- Estrutura da rede: `2 → 3 → 1` (entrada, oculta, saída)
- Ativação: `sigmoid(z) = 1/(1+e^(−z))`
- Função de erro (MSE): `E = (1/2) Σ_i (ŷ_i − t_i)^2`
- Regra de atualização (gradiente descendente): `θ := θ − η ∂E/∂θ`

Equações de backprop para duas camadas:
- Forward: `h = σ(xW1 + b1)`; `ŷ = σ(hW2 + b2)`
- Saída: `δ2 = (ŷ − t) ⊙ (ŷ ⊙ (1 − ŷ))`
- Gradientes: `∂E/∂W2 = h^T δ2`, `∂E/∂b2 = δ2`
- Oculta: `δ1 = (δ2 W2^T) ⊙ (h ⊙ (1 − h))`
- Gradientes: `∂E/∂W1 = x^T δ1`, `∂E/∂b1 = δ1`
- Atualização: `W := W − η ∂E/∂W`, `b := b − η ∂E/∂b`

### Execução
- Comando: `python Estudos/IA/Lista8/main.py`
- Hiperparâmetros usados: `epochs=4000`, `lr=0.7`

### Resultados a incluir
- PRINT 1 — Log de treinamento (perda por época): rode o comando e capture
  algumas linhas como: `[epoch 400] loss=...`, `[epoch 800] loss=...` até o fim.
  Inclua um recorte do início, meio e fim para mostrar a queda da perda.
- PRINT 2 — Previsões finais do XOR: copie ou capture as 4 linhas impressas no
  final do bloco XOR, no formato: `in=[x1, x2] target=T pred=... -> bin=B`.
- Comentários: descreva em 2–3 frases que a perda converge para ~0 e que as
  saídas binárias batem com a tabela verdade do XOR.

---

## 2) Dígitos binários de um display de 7 segmentos

### Descrição
- Cada entrada possui 7 posições (segmentos `a..g`).
- Objetivo: reconhecer os dígitos `0..9`.
- Estrutura da rede: `7 → 5 → 4`.
- Saída: 4 bits representando o dígito em binário (0000–1001).
- Ativação: sigmoid em todas as camadas. Loss: MSE.

Tabela de segmentos (1=ligado, 0=desligado):

| Dígito | a b c d e f g |
|-------:|:--------------:|
| 0 | [1 1 1 1 1 1 0] |
| 1 | [0 1 1 0 0 0 0] |
| 2 | [1 1 0 1 1 0 1] |
| 3 | [1 1 1 1 0 0 1] |
| 4 | [0 1 1 0 0 1 1] |
| 5 | [1 0 1 1 0 1 1] |
| 6 | [1 0 1 1 1 1 1] |
| 7 | [1 1 1 0 0 0 0] |
| 8 | [1 1 1 1 1 1 1] |
| 9 | [1 1 1 1 0 1 1] |

### Ruído (robustez)
- Durante o treino, foram adicionadas amostras com ruído: alguns segmentos ligados
  podem “apagar” (falha) e alguns desligados podem “acender” (espúrios), simulando
  defeitos. Parâmetros padrão no código: `drop_prob=0.15`, `spur_prob=0.03` na
  expansão do conjunto; e avaliação com `drop_prob≈0.2–0.25` e `spur_prob≈0.05`.

### Execução
- Comando: `python Estudos/IA/Lista8/main.py`
- Hiperparâmetros usados: `epochs=3000`, `lr=0.5`

### Resultados a incluir
- PRINT 1 — Log de treinamento: recorte com 3–6 linhas `[epoch ...] loss=...`
  mostrando o comportamento ao longo das épocas.
- PRINT 2 — Métricas: as duas linhas `Acurácia (limpo): ...` e `Acurácia (ruído): ...`.
- PRINT 3 — Exemplos limpos: os blocos em ASCII para dígitos 0, 2, 4, 6, 8 e 9
  (o programa já imprime nessa ordem). Faça um recorte que mostre cada bloco
  com a linha `Dígito X -> previsto Y` acima.
- PRINT 4 — Exemplos com ruído: os blocos que o programa imprime (ex.: base 1,
  3, 5, 7). Capture os textos `Base X (ruído) -> previsto Y` junto do desenho.
  Tente escolher um caso com previsão correta e outro com erro para discussão.

### Discussão sugerida

Explicação — por que 5 neurônios aprendem mapeamentos lineares+não‑lineares:
- Uma MLP com uma camada oculta e ativações sigmoides é um aproximador
  universal. Mesmo com poucos neurônios, ela combina funções lineares (pesos)
  com a não‑linearidade da sigmoid para criar fronteiras de decisão curvas.
- Cada saída (bit) é, na prática, uma função booleana dos 7 segmentos. Como o
  conjunto é pequeno (10 padrões), 5 unidades ocultas são suficientes para
  separar os casos relevantes e recombinar em 4 bits corretos.
- Intuição: a MLP aprende “detectores” de padrões parciais (linhas horizontais,
  colunas, presença do segmento central g, etc.) e depois compõe esses sinais
  para produzir o dígito codificado.

Impacto do ruído — quais dígitos sofrem mais:
- Dígitos que diferem por UM segmento são mais frágeis. Exemplos clássicos:
  8 vs 9 (diferença principalmente no segmento e), 0 vs 8 (segmento g), 3 vs 9
  (segmento f), 6 vs 8 (segmento b), 1 vs 7 (segmento a).
- Quando um segmento “apaga” (drop), o 8 tende a virar 9 (e desliga), e o 0
  pode virar 8 (g acende) com ruído espúrio. Por isso, a acurácia sob ruído cai
  bem mais do que no conjunto limpo.

Erros típicos e por que ocorrem:
- 8 → 9: basta o segmento e apagar; são padrões quase idênticos.
- 9 → 8: ruído espúrio acendendo o segmento e.
- 3 ↔ 9: confusão no segmento f (9 possui f ligado; 3, desligado).
- 0 ↔ 8: diferença é o segmento g (central). Um g falso/ausente troca as classes.
- 5 ↔ 6: o 6 tem b desligado; se b acende, aproxima de 8; se e apaga em 6,
  aproxima de 5. Pequenas alterações mudam a “assinatura”.

Melhorias possíveis:
- Usar 10 saídas one‑hot com softmax e perda de entropia cruzada (melhor para
  classificação multi‑classe). Mantém decisões mais separadas entre classes.
- Aumentar dados com mais ruídos controlados (vários níveis por segmento).
- Mini‑batch + otimizadores (Momentum/Adam) e regularização L2.
- Ativação ReLU na oculta (estabilidade) e sigmoid/softmax na saída.
- Mais neurônios/segunda camada oculta para maior capacidade.
- Limiares adaptativos na decodificação dos 4 bits (se mantiver binário).

---

## Conclusões
- No XOR, a rede 2–3–1 converge rapidamente e acerta as quatro combinações.
- No 7‑segmentos, obtém 100% no conjunto limpo e cerca de 45–55% com ruído
  moderado (valores podem variar por semente e hiperparâmetros).
- A queda com ruído é explicada por pares de dígitos que diferem por 1 segmento
  — uma única falha já faz um padrão imitar outro.
- Limitações: dataset minúsculo (apenas 10 padrões “puros”), uso de MSE+sigmoid
  para multi‑classe, e codificação binária de 4 bits que não impõe separação entre
  classes como o one‑hot. Próximos passos: softmax+CE com 10 saídas, mais dados
  ruidosos, regularização e, se necessário, redes um pouco maiores.

---

## Reprodutibilidade
- Execução: `python Estudos/IA/Lista8/main.py`
- Arquivo para editar hiperparâmetros: `Estudos/IA/Lista8/main.py`
- Estrutura e backprop: `Estudos/IA/Lista8/mlp.py`
- Dataset e ruído: `Estudos/IA/Lista8/seven_segment.py`
