# rule.py
# Estruturas de suporte para nós e conexões das árvores (ID3, C4.5, CART)
# Refatorado: reordenação de classes, docstrings mais objetivas, pequenas
# mudanças de nomes internos e estilo — mantendo a API pública compatível.

from collections import Counter
from typing import Any, List, Optional


# =========================
# Utilitários de estrutura
# =========================

class Cell:
    """Par (nome, frequência) para sumarizar a distribuição de classes."""
    def __init__(self, name: Any, frequency: int):
        self.name = name
        self.frequency = frequency


class Connection:
    """Aresta que liga um nó a seu filho, rotulada por um valor/condição."""
    def __init__(self, result_name: Any):
        self.result_name = result_name
        self.rule: Optional[Any] = None  # nó filho (regra) conectado


# ==============================
# Mixin para estatísticas comuns
# ==============================

class _StatsMixin:
    """
    Mixin interno para popular estatísticas de classe em cada nó.
    Mantém os mesmos atributos esperados pelo restante do projeto:
    data_n, result, result_frequencia, result_ns.
    """
    def _populate_stats(self, data_results: List[Any]) -> None:
        self.data_n = len(data_results)
        counts = Counter(data_results)

        # Classe majoritária
        self.result, self.result_frequencia = counts.most_common(1)[0]

        # Lista de células (nome/frequência) para exibição
        self.result_ns = [Cell(cls_name, freq) for cls_name, freq in counts.items()]

    @staticmethod
    def _format_result_list(cells: List[Cell], quoted_names: bool = False) -> str:
        if quoted_names:
            return "/".join([f"'{c.name}' {c.frequency}" for c in cells])
        return "/".join([f"{c.name} {c.frequency}" for c in cells])


# ===========================
# Regras específicas por crit
# ===========================

class DecisionRuleID3(_StatsMixin):
    """
    Regra de decisão para ID3 (ganho de informação).
    Atributos públicos (compatíveis):
      - attribute, information_gain, level, connections
      - result, result_frequencia, result_ns, data_n
    """
    def __init__(self, attribute: Any, information_gain: float,
                 data, data_results: List[Any], level: int):
        self.attribute = attribute
        self.information_gain = information_gain
        self.level = level
        self.connections: List[Connection] = []
        self._populate_stats(data_results)

    def __str__(self) -> str:
        lista = self._format_result_list(self.result_ns, quoted_names=True)
        return (f"Nível {self.level} - {self.result} ({lista}) | "
                f"Ganho: {self.information_gain:.4f} | Atributo: {self.attribute}")


class DecisionRuleC45(_StatsMixin):
    """
    Regra de decisão para C4.5 (razão de ganho).
    Atributos públicos (compatíveis):
      - attribute, gain_ratio, value (limiar p/ numéricos), level, connections
      - result, result_frequencia, result_ns, data_n
    """
    def __init__(self, attribute: Any, gain_ratio: float,
                 data, data_results: List[Any], level: int):
        self.attribute = attribute
        self.gain_ratio = gain_ratio
        self.level = level
        self.value: Optional[float] = None  # limiar para contínuos, quando aplicável
        self.connections: List[Connection] = []
        self._populate_stats(data_results)

    def __str__(self) -> str:
        lista = self._format_result_list(self.result_ns, quoted_names=False)
        return (f"Nível {self.level} - {self.result} ({lista}) | "
                f"Razão: {self.gain_ratio:.4f} | Atributo: {self.attribute}")


class DecisionRuleCART(_StatsMixin):
    """
    Regra de decisão para CART (índice Gini; sempre binária).
    Atributos públicos (compatíveis):
      - attribute, gini, value (valor/rotulo usado p/ split), level, connections
      - result, result_frequencia, result_ns, data_n
    """
    def __init__(self, attribute: Any, gini: float,
                 data, data_results: List[Any], level: int):
        self.attribute = attribute
        self.gini = gini
        self.level = level
        self.value: Optional[Any] = None  # limiar (numérico) ou valor categórico testado
        self.connections: List[Connection] = []
        self._populate_stats(data_results)

    def __str__(self) -> str:
        lista = self._format_result_list(self.result_ns, quoted_names=False)
        return (f"Nível {self.level} - {self.result} ({lista}) | "
                f"Gini: {self.gini:.4f} | Atributo: {self.attribute}")
