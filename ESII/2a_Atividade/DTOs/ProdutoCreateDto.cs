namespace ProjetoPedidos.DTOs;

public class ProdutoCreateDto
{
    public string Nome { get; set; } = string.Empty;
    public decimal Preco { get; set; }
    public int Estoque { get; set; }
}
