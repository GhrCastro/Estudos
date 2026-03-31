CREATE TABLE IF NOT EXISTS `__EFMigrationsHistory` (
    `MigrationId` varchar(150) CHARACTER SET utf8mb4 NOT NULL,
    `ProductVersion` varchar(32) CHARACTER SET utf8mb4 NOT NULL,
    CONSTRAINT `PK___EFMigrationsHistory` PRIMARY KEY (`MigrationId`)
) CHARACTER SET=utf8mb4;

START TRANSACTION;

ALTER DATABASE CHARACTER SET utf8mb4;

CREATE TABLE `Pedidos` (
    `Id` int NOT NULL AUTO_INCREMENT,
    `Data` datetime NOT NULL,
    `ValorTotal` decimal(10,2) NOT NULL,
    CONSTRAINT `PK_Pedidos` PRIMARY KEY (`Id`)
) CHARACTER SET=utf8mb4;

CREATE TABLE `Produtos` (
    `Id` int NOT NULL AUTO_INCREMENT,
    `Nome` varchar(120) CHARACTER SET utf8mb4 NOT NULL,
    `Preco` decimal(10,2) NOT NULL,
    `Estoque` int NOT NULL,
    `TipoProduto` varchar(13) CHARACTER SET utf8mb4 NOT NULL,
    `Voltagem` int NULL,
    `DataValidade` datetime NULL,
    CONSTRAINT `PK_Produtos` PRIMARY KEY (`Id`)
) CHARACTER SET=utf8mb4;

CREATE TABLE `Itens` (
    `CodigoItem` int NOT NULL AUTO_INCREMENT,
    `Quantidade` int NOT NULL,
    `ValorItem` decimal(10,2) NOT NULL,
    `PedidoId` int NOT NULL,
    `ProdutoId` int NOT NULL,
    CONSTRAINT `PK_Itens` PRIMARY KEY (`CodigoItem`),
    CONSTRAINT `FK_Itens_Pedidos_PedidoId` FOREIGN KEY (`PedidoId`) REFERENCES `Pedidos` (`Id`) ON DELETE CASCADE,
    CONSTRAINT `FK_Itens_Produtos_ProdutoId` FOREIGN KEY (`ProdutoId`) REFERENCES `Produtos` (`Id`) ON DELETE RESTRICT
) CHARACTER SET=utf8mb4;

CREATE INDEX `IX_Itens_PedidoId` ON `Itens` (`PedidoId`);

CREATE INDEX `IX_Itens_ProdutoId` ON `Itens` (`ProdutoId`);

INSERT INTO `__EFMigrationsHistory` (`MigrationId`, `ProductVersion`)
VALUES ('20260331184805_InitialCreate', '8.0.5');

COMMIT;

