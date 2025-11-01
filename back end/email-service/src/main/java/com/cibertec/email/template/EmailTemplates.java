package com.cibertec.email.template;

public class EmailTemplates {
    public static final String HTML_TEMPLATE_USER = """
            <!DOCTYPE html>
            <html lang="es">
            <head>
                <meta charset="UTF-8">
                <title>Bienvenido a La Gula Pizzería</title>
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <style>
                    body {
                        background: #fff7ed;
                        margin: 0;
                        padding: 0;
                        font-family: 'Segoe UI', Arial, sans-serif;
                    }
                    .container {
                        background: #fff;
                        border-radius: 12px;
                        box-shadow: 0 4px 25px rgba(0,0,0,0.08);
                        max-width: 620px;
                        margin: 40px auto;
                        overflow: hidden;
                    }
                    .header {
                        background: linear-gradient(90deg, #d62828, #f77f00);
                        color: #fff;
                        text-align: center;
                        padding: 28px 16px;
                    }
                    .header img {
                        width: 90px;
                        margin-bottom: 12px;
                    }
                    .header h1 {
                        margin: 0;
                        font-size: 26px;
                        letter-spacing: 1px;
                    }
                    .content {
                        padding: 28px;
                        color: #333;
                    }
                    .content h2 {
                        color: #d62828;
                        font-size: 22px;
                        margin-bottom: 18px;
                        text-align: center;
                    }
                    .details {
                        width: 100%%;
                        border-collapse: collapse;
                    }
                    .details td {
                        padding: 10px 0;
                        font-size: 15px;
                        vertical-align: top;
                    }
                    .details td.label {
                        font-weight: 600;
                        color: #444;
                        width: 35%%;
                    }
                    .details td.value {
                        color: #222;
                    }
                    .footer {
                        text-align: center;
                        font-size: 13px;
                        color: #777;
                        padding: 22px;
                        background: #fff7ed;
                    }
                    .footer strong {
                        color: #d62828;
                    }
                </style>
            </head>
            <body>
                <div class="container">
                    <div class="header">
                        <h1>🍕 La Gula Pizzería</h1>
                    </div>
                    <div class="content">
                        <h2>¡Bienvenido nuevo %s!</h2>
                        <p style="text-align:center; font-size:16px;">
                            Estamos encantados de tenerte con nosotros, <strong>%s</strong>.
                        </p>
                        <table class="details">
                            <tr><td class="label">Correo electrónico:</td><td class="value">%s</td></tr>
                            <tr><td class="label">Teléfono:</td><td class="value">%s</td></tr>
                            <tr><td class="label">Estado:</td><td class="value">%s</td></tr>
                            <tr><td class="label">Fecha de registro:</td><td class="value">%s</td></tr>
                        </table>
                    </div>
                    <div class="footer">
                        ¡Gracias por unirte a nuestra familia!<br>
                        <strong>La Gula Pizzería</strong> © %s
                    </div>
                </div>
            </body>
            </html>
            """;

    public static final String HTML_TEMPLATE_ORDER = """
            <!DOCTYPE html>
            <html lang="es">
            <head>
                <meta charset="UTF-8">
                <title>Confirmación de Pedido - La Gula Pizzería</title>
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <style>
                    body {
                        background: #fff7ed;
                        margin: 0;
                        padding: 0;
                        font-family: 'Segoe UI', Arial, sans-serif;
                    }
                    .container {
                        background: #fff;
                        border-radius: 12px;
                        box-shadow: 0 4px 25px rgba(0,0,0,0.08);
                        max-width: 640px;
                        margin: 40px auto;
                        overflow: hidden;
                    }
                    .header {
                        background: linear-gradient(90deg, #d62828, #f77f00);
                        color: #fff;
                        text-align: center;
                        padding: 28px 16px;
                    }
                    .header h1 {
                        margin: 0;
                        font-size: 26px;
                        letter-spacing: 1px;
                    }
                    .content {
                        padding: 28px;
                        color: #333;
                    }
                    .content h2 {
                        color: #d62828;
                        font-size: 22px;
                        margin-bottom: 18px;
                        text-align: center;
                    }
                    .details, .items {
                        width: 100%%;
                        border-collapse: collapse;
                    }
                    .details td, .items td {
                        padding: 8px 0;
                        font-size: 15px;
                        vertical-align: top;
                    }
                    .details td.label {
                        font-weight: 600;
                        color: #444;
                        width: 35%%;
                    }
                    .details td.value {
                        color: #222;
                    }
                    .items th {
                        text-align: left;
                        padding-top: 10px;
                        color: #d62828;
                        font-size: 16px;
                        border-bottom: 2px solid #f77f00;
                        padding-bottom: 6px;
                    }
                    .topping {
                        font-size: 14px;
                        color: #555;
                        margin-left: 10px;
                    }
                    .footer {
                        text-align: center;
                        font-size: 13px;
                        color: #777;
                        padding: 22px;
                        background: #fff7ed;
                    }
                    .footer strong {
                        color: #d62828;
                    }
                </style>
            </head>
            <body>
                <div class="container">
                    <div class="header">
                        <h1>🍕 La Gula Pizzería</h1>
                    </div>
                    <div class="content">
                        <h2>Confirmación de tu pedido #%s</h2>
                        <p style="text-align:center; font-size:16px;">
                            ¡Hola <strong>%s</strong>! Gracias por pedir con <strong>La Gula Pizzería</strong>.<br>
                            Aquí tienes los detalles de tu pedido:
                        </p>
                        <table class="details">
                            <tr><td class="label">Estado del pedido:</td><td class="value">%s</td></tr>
                            <tr><td class="label">Correo:</td><td class="value">%s</td></tr>
                            <tr><td class="label">Monto total:</td><td class="value">S/ %s</td></tr>
                        </table>
                        <h2 style="margin-top:30px;">🍕 Detalle de pizzas</h2>
                        %s
                    </div>
                    <div class="footer">
                        ¡Esperamos que disfrutes tu pizza!<br>
                        <strong>La Gula Pizzería</strong> © %s
                    </div>
                </div>
            </body>
            </html>
            """;

}
