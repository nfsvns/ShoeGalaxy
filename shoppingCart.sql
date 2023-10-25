USE [master]
GO
/****** Object:  Database [ShoeGalaxy]    Script Date: 10/25/2023 4:50:32 PM ******/
CREATE DATABASE [ShoeGalaxy]

USE [ShoeGalaxy]
GO
/****** Object:  Table [dbo].[ShoppingCarts]    Script Date: 10/25/2023 4:50:32 PM ******/
CREATE TABLE [dbo].[ShoppingCarts](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[username] [nvarchar](50) NULL,
	[product_id] [int] NULL,
	[image] [nvarchar](100) NULL,
	[name] [nvarchar](50) NULL,
	[size] [int] NULL,
	[price] [float] NULL,
	[qty] [int] NULL,
	[total] [float] NULL,
 CONSTRAINT [PK_ShoppingCarts] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

ALTER TABLE [dbo].[ShoppingCarts]  WITH CHECK ADD  CONSTRAINT [FK_ShoppingCarts_Accounts] FOREIGN KEY([username])
REFERENCES [dbo].[Accounts] ([username])
GO
ALTER TABLE [dbo].[ShoppingCarts] CHECK CONSTRAINT [FK_ShoppingCarts_Accounts]
GO
ALTER TABLE [dbo].[ShoppingCarts]  WITH CHECK ADD  CONSTRAINT [FK_ShoppingCarts_Products] FOREIGN KEY([product_id])
REFERENCES [dbo].[Products] ([id])
GO
ALTER TABLE [dbo].[ShoppingCarts] CHECK CONSTRAINT [FK_ShoppingCarts_Products]
GO


USE [master]
GO
ALTER DATABASE [ShoeGalaxy] SET  READ_WRITE 
GO
