Lista 8 — Redes Neurais com Backpropagation (from scratch)

Este diretório contém uma implementação simples, sem bibliotecas de ML, para:
- XOR
- Dígitos 0–9 a partir de um display de 7 segmentos

Arquivos:
- main.py: ponto de entrada; executa as duas demonstrações.
- mlp.py: MLP 7-5-4 (sigmoid + MSE) implementada do zero.
- seven_segment.py: dataset e utilitários do display de 7 segmentos.

Como executar (Python 3.8+):
python Estudos/I.A/Lista8/main.py

Observação: a saída usa codificação binária de 4 bits para representar dígitos 0–9.
Se quiser usar one-hot com 10 saídas, ajuste o tamanho da camada de saída e as
funções de codificação em seven_segment.py.

