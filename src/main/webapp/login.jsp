<%@ page contentType="text/html; charset=UTF-8" %>
    <!DOCTYPE html>
    <html lang="en">

    <head>
        <title>Login Page</title>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    </head>

    <body>

        <div class="container">
            <div class="row justify-content-center">
                <div class="col-md-6 col-lg-5">
                    <h3 class="text-center text-secondary mt-5 mb-3">User Login</h3>
                    <form class="border rounded w-100 mb-5 mx-auto px-3 pt-3 bg-light" method="post">
                        <div class="form-group">
                            <label for="username">Username</label>
                            <input id="username" name="username" type="text" class="form-control" placeholder="Username"
                                value="${username}">
                        </div>
                        <div class="form-group">
                            <label for="password">Password</label>
                            <input id="password" name="password" type="password" class="form-control"
                                placeholder="Password">
                        </div>
                        <div class="mb-3 form-check">
                            <input type="checkbox" class="form-check-input" id="remember-me" name="rememberMe">
                            <label class="form-check-label" for="remember-me">Remember username & password</label>
                        </div>
                        <% if (request.getAttribute("flashMessage") !=null) { %>
                            <div class="alert alert-danger" role="alert">
                                ${flashMessage}
                            </div>
                            <% } %>
                                <div class="form-group">
                                    <button class="btn btn-success px-5">Login</button>
                                </div>

                                <div>Or <a href="/register">sign up</a> for an account</div>
                    </form>

                </div>
            </div>
        </div>

    </body>

    </html>