use ShoeGalaxy
go
/* asd
CREATE PROCEDURE DeleteProductAndRelatedData
    @id INT
AS
BEGIN
    DELETE FROM Images WHERE product_id = @id;
	DELETE FROM discount_product WHERE product_id = @id;
    DELETE FROM Sizes WHERE product_id = @id;
    DELETE FROM replys WHERE comment_id IN (SELECT id FROM comments WHERE product_id = @id);
    DELETE FROM comments WHERE product_id = @id;
	DELETE FROM Categories WHERE id = (SELECT category_id FROM Products WHERE id = @id);
	DELETE FROM orderdetails WHERE product_id = @id;
    DELETE FROM Orders WHERE id IN (SELECT DISTINCT order_id FROM orderdetails WHERE id = @id);
    DELETE FROM Products WHERE id = @id;	
END
go
*/

CREATE PROCEDURE DeleteAccountAndRelatedData
    @Username NVARCHAR(255)
AS
BEGIN
    DELETE FROM OrderDetails
    WHERE order_id IN (SELECT id FROM Orders WHERE username = @Username);
    DELETE FROM Orders
    WHERE username = @Username;
    DELETE FROM Addresses
    WHERE account_username = @Username;
    DELETE FROM Authorities
    WHERE username = @Username;
    DELETE FROM Accounts
    WHERE username = @Username;
END;
