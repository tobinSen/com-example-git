<html lang="en">
<head>
    <meta charset="utf-8"/>
    <title>Document</title>
    <style type="text/css">
        html, body {
            margin: 0;
            padding: 0;
        }

        body {
            color: #000;
            font-family: SimSun;
            font-size: 20px;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            border-spacing: 0;
            padding: 0;
            margin: 0;
        }

        td, th {
            padding: 0;
            margin: 0;
            font-size: 100%;
        }

        @media print {
            @page {
                margin: 0;
                padding: 0;
                margin-bottom: 20px;
            }
        }

        .no-bold {
            font-weight: normal;
        }

        .breakline {
            word-wrap: break-word;
            word-break: normal;
        }

        .footer {
            margin-top: 10px;
        }

        @page {
            margin: 20mm 5mm 40mm 5mm;
        }

        #header {
            position: running(header);
        }

        #footer {
            position: running(footer);
        }

        @page {
            @top-center {
                content: element(header);
            }
            @bottom-center {
                content: element(footer)
            }
        }

        #pages:before {
            content: counter(page);
            font-size: 10px;
        }

        #pages:after {
            content: counter(pages);
            font-size: 10px;
        }
    </style>
</head>
<body>
<div>
    <div id="header" style="margin-top: 0px;">
        <span>页眉页眉页眉页眉页眉页眉页眉页眉页眉页眉页眉页眉页眉页眉页眉页眉页眉页眉</span>
    </div>
    <div id="footer">
        <div style="text-align: center;">第<span id="pages">页，共</span>页</div>
        <span style="margin-top:5px;">页底页底页底页底页底页底页底页底页底页底页底页底页底页底页底页底页底页底</span>
    </div>
    <br/>
    <div>
        <table class="biaoge1">
            <tr>
                <td width="20%">编号1</td>
                <td width="30%">名称</td>
                <td width="20%">编号2</td>
                <td width="30%" align="center">编号3</td>
            </tr>
				<#list insurantList as insurant>
					<tr>
                        <td>${insurant.inssueNum}</td>
                        <td>${insurant.cname}</td>
                        <td>${insurant.cardNum}</td>
                        <td align="center">${insurant.brithday}</td>
                    </tr>
                </#list>
        </table>
    </div>

</div>
</body>
</html>