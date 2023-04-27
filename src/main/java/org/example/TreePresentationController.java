package org.example;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

/**
 * Контроллер отвечающий за представление списка.
 */
@Path("/")
public class TreePresentationController {
    private final List<String> list;

    private List<Node> nodes;
    private int treeDeep = 0;


    Node root = new Node(treeDeep, "Start");

    /**
     * Запоминает список, с которым будет работать.
     *
     * @param list список, с которым будет работать контроллер.
     */
    public TreePresentationController(List<String> list) {
        this.list = list;
    }

    /**
     * Пример вывода простого текста.
     */
    @GET
    @Path("example")
    @Produces("text/plain")
    public String getSimpleText() {
        return "hello world";
    }

    /**
     * Выводит HTML-страницу со списком, ссылками на страницы редактирования и копкой добавления записи.
     *
     * @return HTML-страница со списком.
     */
    @GET
    @Path("/")
    @Produces("text/html")
    public String getTree() {
        String result =
                "<html>" +
                        "  <head>" +
                        "    <title>Работа с деревом</title>" +
                        "  </head>" +
                        "  <body>" +
                        "    <h1>Дерево</h1>" +
                        "    <ul>";
        StringBuilder html = new StringBuilder();
        result += root.createHTMLStringForEachNode(html);
        result += "    </ul>" +
                "      <br/>" +
                "      <form method=\"post\" action=\"add_tree_node\">" +
                "        <input type=\"submit\" value=\"Add node\"/>" +
                "      </form>" +
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
     * Пример обработки POST запроса.
     * Добавляет одну случайную запись в список и перенаправляет пользователя на основную страницу со списком.
     *
     * @return перенаправление на основную страницу со списком.
     */
    @POST
    @Path("add/{id}")
    @Produces("text/html")
    public Response addTreeNode(@PathParam("id") int id) {
        root.searchById(id).addChild(new Node(treeDeep + 1, "Z"));
        treeDeep++;
        try {
            return Response.seeOther(new URI("/")).build();
        } catch (URISyntaxException e) {
            throw new IllegalStateException("Ошибка построения URI для перенаправления");
        }
    }

    /**
     * Пример обработки POST запроса.
     * Добавляет одну случайную запись в список и перенаправляет пользователя на основную страницу со списком.
     *
     * @return перенаправление на основную страницу со списком.
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
     * Выводит страничку для редактирования одного элемента.
     *
     * @param itemId индекс элемента списка.
     * @return страничка для редактирования одного элемента.
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
                        "      <input type=\"submit\"/>";
        result +=
                "            </form>" +
                        "  </body>" +
                        "</html>";
        return result;
    }

    /**
     * Редактирует элемент списка на основе полученных данных.
     *
     * @param itemId индекс элемента списка.
     * @return перенаправление на основную страницу со списком.
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