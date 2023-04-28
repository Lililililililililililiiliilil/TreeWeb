package org.example;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Контроллер отвечающий за представление дерева.
 */
@Path("/")
public class TreePresentationController {


    private int treeDeep = 0;


    Node root = new Node(treeDeep, "Start");

    /**
     * Конструктор по умолчанию
     */
    public TreePresentationController() {
    }


    /**
     * Выводит заглавную страницу дерева.
     *
     * @return HTML-страница с деревом.
     */
    @GET
    @Path("/")
    @Produces("text/html")
    public String getTree() {
        String result =
                "<html>" +
                        "  <head>" +
                        "    <title>Работа с деревом \uD83C\uDF33 </title>" +
                        "    <link rel=\"icon\" href=\"favicon.ico\" type=\"image/x-icon\">" +
                        "    <link rel=\"manifest\" href=\"manifest.webmanifest\">" +
                        "  </head>" +
                        "  <body>" +
                        "    <h1>Дерево \uD83C\uDF33 </h1>" +
                        "    <ul>";
        StringBuilder html = new StringBuilder();
        result += root.createHTMLStringForEachNode(html);
        result += "    </ul>" +
                "      <br/>" +
                "  </body>" +
                "</html>";
        return result;
    }

    /**
     * Метод для создания большого файла
     *
     * @return заглушка страницы.
     */

    @GET
    @Path("/file")
    @Produces("text/file")
    public String fileWrite() throws IOException {
        FileWriter fileWriter = new FileWriter("output.txt");

        for (int i = 0; i < 200000000; i++) {
            fileWriter.append((char) i);
        }
        return "\"hello world\"";
    }


    /**
     * POST запрос.
     * Добавляет новый узел с предопределённым именем и перенаправляет пользователя на заглавную страницу.
     *
     * @return перенаправление на заглавную страницу с деревом.
     */
    @POST
    @Path("add/{id}")
    @Produces("text/html")
    public Response addTreeNode(@PathParam("id") int id) {
        root.searchById(id).addChild(new Node(treeDeep + 1, "New node"));
        treeDeep++;
        try {
            return Response.seeOther(new URI("/")).build();
        } catch (URISyntaxException e) {
            throw new IllegalStateException("Ошибка построения URI для перенаправления");
        }
    }

    /**
     * Пример обработки POST запроса.
     * Удаляет выбранный узел дерева со всеми вложенными и перенаправляет пользователя на заглавную страницу.
     *
     * @return перенаправление на заглавную страницу с деревом.
     */
    @POST
    @Path("delete/{id}")
    @Produces("text/html")
    public Response deleteTreeNode(@PathParam("id") int id) {
        root.deleteById(id);
        try {
            return Response.seeOther(new URI("/")).build();
        } catch (URISyntaxException e) {
            throw new IllegalStateException("Ошибка построения URI для перенаправления");
        }
    }

    /**
     * Выводит страничку для редактирования узла.
     *
     * @param itemId идентификатор узла.
     * @return страничка для редактирования узла.
     */
    @GET
    @Path("/edit/{id}")
    @Produces("text/html")
    public String getEditPage(@PathParam("id") int itemId) {
        String nodeName = root.searchById(itemId).getName();

        String result =
                "<html>" +
                        "  <head>" +
                        "    <title>Редактирование узла</title>" +
                        "  </head>" +
                        "  <body>" +
                        "    <h1>Редактирование узла</h1>" +
                        "    <form method=\"post\" action=\"/edit/" + itemId + "\">" +
                        "      <p>Название узла</p>" +
                        "      <input type=\"text\" name=\"value\" value=\"" + nodeName + "\"/>" +
                        "      <input type=\"submit\" value=\"Готово\" />";
        result +=
                "            </form>" +
                        "  </body>" +
                        "</html>";
        return result;
    }

    /**
     * Редактирует узел на основе полученных данных.
     *
     * @param itemId идентификатор узла.
     * @return перенаправление на заглавную страницу с деревом.
     */
    @POST
    @Path("/edit/{id}")
    @Produces("text/html")
    public Response editItem(@PathParam("id") int itemId, @FormParam("value") String itemValue) {
        root.searchById(itemId).setName(itemValue);
        try {
            return Response.seeOther(new URI("/")).build();
        } catch (URISyntaxException e) {
            throw new IllegalStateException("Ошибка построения URI для перенаправления");
        }
    }


}