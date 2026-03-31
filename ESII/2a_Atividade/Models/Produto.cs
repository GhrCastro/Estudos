namespace ProjetoPedidos.Models;

public class Produto
{
    public int Id { get; set; }
    public string Nome { get; set; } = string.Empty;
    public decimal Preco { get; set; }
    public int Estoque { get; set; }
    public ICollection<Item> Itens { get; set; } = new List<Item>();
}
