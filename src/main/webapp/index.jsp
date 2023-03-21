<%@ page contentType="text/html; charset=UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <!DOCTYPE html>
        <html lang="en">

        <head>
            <title>Danh sách sản phẩm</title>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1">
            <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
            <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
            <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
            <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
        </head>

        <body style="background-color: #f8f8f8">

            <div class="container-fluid p-5">
                <div class="row mb-5">
                    <div class="col-md-6">
                        <h3>Product Management</h3>
                    </div>
                    <div class="col-md-6 text-right">
                        Xin chào <span class="text-danger">${username}</span> | <a href="/logout">Logout</a>
                    </div>
                </div>
                <div class="row rounded border p-3">
                    <div class="col-md-4">
                        <h4 class="text-success">Thêm sản phẩm mới</h4>
                        <form class="mt-3" method="post">
                            <div class="form-group">
                                <label for="product-name">Tên sản phẩm</label>
                                <input class="form-control" type="text" placeholder="Nhập tên sản phẩm"
                                    id="product-name" name="name" value="${name}">
                            </div>
                            <div class="form-group">
                                <label for="price">Giá sản phẩm</label>
                                <input class="form-control" type="number" placeholder="Nhập giá bán" id="price"
                                    name="price">
                            </div>
                            <div class="form-group">
                                <button class="btn btn-success mr-2">Thêm sản phẩm</button>
                            </div>
                            <% if (request.getAttribute("flashMessage") !=null) { %>
                                <div class="alert alert-danger">
                                    ${flashMessage}
                                </div>
                                <% } %>
                        </form>
                    </div>
                    <div class="col-md-8">
                        <h4 class="text-success">Danh sách sản phẩm</h4>
                        <p>Chọn một sản phẩm cụ thể để xem chi tiết</p>
                        <table class="table table-striped">
                            <thead>
                                <tr>
                                    <th>STT</th>
                                    <th>Tên sản phẩm</th>
                                    <th>Giá</th>
                                    <th>Thao tác</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${products}" var="product">
                                    <tr>
                                        <td>${product.id}</td>
                                        <td><a href="#">${product.name}</a></td>
                                        <td>$ ${product.price}</td>
                                        <td>
                                            <a class="delete" data-id="${product.id}">Xóa</a>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
            <script>
                const deletes = document.querySelectorAll(".delete");
                deletes.forEach((d) => {
                    d.onclick = async function() {
                        const id = this.dataset.id;
                        console.log(id);
                        if (confirm("Delete this product?")) {
                            await handleDelete(id);
                            location.reload();
                        }
                    }
                })

                async function handleDelete(id) {
                    try {
                        const res = await fetch("/?id=" + id, {
                            method: "DELETE"
                        })
                        const json = await res.json();
                        console.log(json);
                        return json;
                    } catch (err) {
                        throw err
                    }
                }
            </script>
        </body>

        </html>