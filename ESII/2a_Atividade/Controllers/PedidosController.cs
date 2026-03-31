using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using ProjetoPedidos.Data;
using ProjetoPedidos.DTOs;
using ProjetoPedidos.Models;

namespace ProjetoPedidos.Controllers;

[ApiController]
[Route("api/[controller]")]
public class PedidosController : ControllerBase
{
    private readonly AppDbContext _context;

    public PedidosController(AppDbContext context)
    {
        _context = context;
    }

    [HttpGet]
    public async Task<ActionResult<IEnumerable<object>>> GetAll()
    {
        var pedidos = await _context.Pedidos
            .AsNoTracking()
            .Include(p => p.Itens)
            .ThenInclude(i => i.Produto)
            .ToListAsync();

        return Ok(pedidos.Select(MapPedido));
    }

    [HttpGet("{id:int}")]
    public async Task<ActionResult<object>> GetById(int id)
    {
        var pedido = await _context.Pedidos
            .AsNoTracking()
            .Include(p => p.Itens)
            .ThenInclude(i => i.Produto)
            .FirstOrDefaultAsync(p => p.Id == id);

        if (pedido is null)
        {
            return NotFound();
        }

        return Ok(MapPedido(pedido));
    }

    [HttpPost]
    public async Task<ActionResult<object>> Create([FromBody] PedidoCreateDto dto)
    {
        var pedido = new Pedido
        {
            Data = dto.Data ?? DateTime.UtcNow,
            ValorTotal = 0
        };

        _context.Pedidos.Add(pedido);
        await _context.SaveChangesAsync();

        return CreatedAtAction(nameof(GetById), new { id = pedido.Id }, MapPedido(pedido));
    }

    [HttpPut("{id:int}")]
    public async Task<ActionResult<object>> Update(int id, [FromBody] PedidoUpdateDto dto)
    {
        var pedido = await _context.Pedidos.FindAsync(id);
        if (pedido is null)
        {
            return NotFound();
        }

        pedido.Data = dto.Data;
        await _context.SaveChangesAsync();

        return Ok(MapPedido(pedido));
    }

    [HttpDelete("{id:int}")]
    public async Task<IActionResult> Delete(int id)
    {
        var pedido = await _context.Pedidos.FindAsync(id);
        if (pedido is null)
        {
            return NotFound();
        }

        _context.Pedidos.Remove(pedido);
        await _context.SaveChangesAsync();

        return NoContent();
    }

    private static object MapPedido(Pedido pedido)
    {
        return new
        {
            pedido.Id,
            pedido.Data,
            pedido.ValorTotal,
            Itens = pedido.Itens.Select(i => new
            {
                i.CodigoItem,
                i.Quantidade,
                i.ValorItem,
                i.ProdutoId,
                NomeProduto = i.Produto?.Nome
            })
        };
    }
}
