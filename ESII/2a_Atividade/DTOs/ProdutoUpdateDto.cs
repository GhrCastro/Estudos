namespace ProjetoPedidos.DTOs;

public class ProdutoUpdateDto
{
    public string Nome { get; set; } = string.Empty;
    public decimal Preco { get; set; }
    public int Estoque { get; set; }
    public int? Voltagem { get; set; }
    public DateTime? DataValidade { get; set; }
}
