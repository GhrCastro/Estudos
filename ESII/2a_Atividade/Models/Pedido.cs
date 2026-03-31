namespace ProjetoPedidos.Models;

public class Pedido
{
    public int Id { get; set; }
    public DateTime Data { get; set; } = DateTime.UtcNow;
    public decimal ValorTotal { get; set; }
    public ICollection<Item> Itens { get; set; } = new List<Item>();
}
