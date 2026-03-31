using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using ProjetoPedidos.Data;
using ProjetoPedidos.DTOs;
using ProjetoPedidos.Models;

namespace ProjetoPedidos.Controllers;

[ApiController]
[Route("api/[controller]")]
public class ItensController : ControllerBase
{
    private readonly AppDbContext _context;

    public ItensController(AppDbContext context)
    {
        _context = context;
    }

    [HttpGet]
    public async Task<ActionResult<IEnumerable<object>>> GetAll()
    {
        var itens = await _context.Itens
            .AsNoTracking()
            .Include(i => i.Pedido)
            .Include(i => i.Produto)
            .ToListAsync();

        return Ok(itens.Select(MapItem));
    }

    [HttpGet("{codigoItem:int}")]
    public async Task<ActionResult<object>> GetById(int codigoItem)
    {
        var item = await _context.Itens
            .AsNoTracking()
            .Include(i => i.Pedido)
            .Include(i => i.Produto)
            .FirstOrDefaultAsync(i => i.CodigoItem == codigoItem);

        if (item is null)
        {
            return NotFound();
        }

        return Ok(MapItem(item));
    }

    [HttpPost]
    public async Task<ActionResult<object>> Create([FromBody] ItemCreateDto dto)
    {
        if (dto.Quantidade <= 0)
        {
            return BadRequest("Quantidade deve ser maior que zero.");
        }

        var pedido = await _context.Pedidos.FindAsync(dto.PedidoId);
        if (pedido is null)
        {
            return BadRequest("Pedido nao encontrado.");
        }

        var produto = await _context.Produtos.FindAsync(dto.ProdutoId);
        if (produto is null)
        {
            return BadRequest("Produto nao encontrado.");
        }

        if (produto.Estoque < dto.Quantidade)
        {
            return BadRequest("Estoque insuficiente.");
        }

        var item = new Item
        {
            PedidoId = pedido.Id,
            ProdutoId = produto.Id,
            Quantidade = dto.Quantidade,
            ValorItem = produto.Preco * dto.Quantidade
        };

        produto.Estoque -= dto.Quantidade;
        _context.Itens.Add(item);
        await _context.SaveChangesAsync();

        await AtualizarTotalPedido(pedido.Id);
        await _context.SaveChangesAsync();

        var itemCriado = await _context.Itens
            .AsNoTracking()
            .Include(i => i.Pedido)
            .Include(i => i.Produto)
            .FirstOrDefaultAsync(i => i.CodigoItem == item.CodigoItem);

        return CreatedAtAction(nameof(GetById), new { codigoItem = item.CodigoItem }, MapItem(itemCriado!));
    }

    [HttpPut("{codigoItem:int}")]
    public async Task<ActionResult<object>> Update(int codigoItem, [FromBody] ItemUpdateDto dto)
    {
        if (dto.Quantidade <= 0)
        {
            return BadRequest("Quantidade deve ser maior que zero.");
        }

        var item = await _context.Itens
            .Include(i => i.Produto)
            .FirstOrDefaultAsync(i => i.CodigoItem == codigoItem);

        if (item is null)
        {
            return NotFound();
        }

        if (item.Produto is null)
        {
            return BadRequest("Produto do item nao encontrado.");
        }

        var diferenca = dto.Quantidade - item.Quantidade;

        if (diferenca > 0 && item.Produto.Estoque < diferenca)
        {
            return BadRequest("Estoque insuficiente para aumentar a quantidade.");
        }

        item.Produto.Estoque -= diferenca;
        item.Quantidade = dto.Quantidade;
        item.ValorItem = item.Produto.Preco * dto.Quantidade;

        await _context.SaveChangesAsync();
        await AtualizarTotalPedido(item.PedidoId);
        await _context.SaveChangesAsync();

        var itemAtualizado = await _context.Itens
            .AsNoTracking()
            .Include(i => i.Pedido)
            .Include(i => i.Produto)
            .FirstOrDefaultAsync(i => i.CodigoItem == item.CodigoItem);

        return Ok(MapItem(itemAtualizado!));
    }

    [HttpDelete("{codigoItem:int}")]
    public async Task<IActionResult> Delete(int codigoItem)
    {
        var item = await _context.Itens
            .Include(i => i.Produto)
            .FirstOrDefaultAsync(i => i.CodigoItem == codigoItem);

        if (item is null)
        {
            return NotFound();
        }

        if (item.Produto is null)
        {
            return BadRequest("Produto do item nao encontrado.");
        }

        item.Produto.Estoque += item.Quantidade;
        var pedidoId = item.PedidoId;

        _context.Itens.Remove(item);
        await _context.SaveChangesAsync();

        await AtualizarTotalPedido(pedidoId);
        await _context.SaveChangesAsync();

        return NoContent();
    }

    private async Task AtualizarTotalPedido(int pedidoId)
    {
        var pedido = await _context.Pedidos.FindAsync(pedidoId);
        if (pedido is null)
        {
            return;
        }

        var total = await _context.Itens
            .Where(i => i.PedidoId == pedidoId)
            .Select(i => (decimal?)i.ValorItem)
            .SumAsync() ?? 0;

        pedido.ValorTotal = total;
    }

    private static object MapItem(Item item)
    {
        return new
        {
            item.CodigoItem,
            item.PedidoId,
            item.ProdutoId,
            item.Quantidade,
            item.ValorItem,
            NomeProduto = item.Produto?.Nome,
            PedidoData = item.Pedido?.Data
        };
    }
}
