import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;


@WebServlet(name = "sample-servlet", urlPatterns = "/servlets")
public class GetAllRequestsServlet extends HttpServlet {

    private static final long serialVersionUID = -8948379822734246956L;

    private Map<String, String> ipsUserAgents;

    private static final Logger log = LoggerFactory.getLogger(GetAllRequestsServlet.class);

    @Override
    public void init() {
        log.info(getServletName() + " initialized");
        ipsUserAgents = new HashMap<>();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String addr = req.getRemoteAddr();
        String userAgent = req.getHeader("User-Agent");

        ipsUserAgents.put(addr, userAgent);

        PrintWriter responseBody = resp.getWriter();

        resp.setContentType("text/html");
        responseBody.println("<h1 align=\"center\">Sample Servlet GET method processing</h1>");

        for (String ip : ipsUserAgents.keySet()) {

            if(ip.equals(addr) && userAgent.equals(ipsUserAgents.get(ip))){
                responseBody.println("<span align=\"center\"><b>Request from: " + ip + "With User-Agent: " + ipsUserAgents.get(ip) + "</b></span>");
            }

            responseBody.println("<span align=\"center\">Request from: " + ip + "With User-Agent: " + ipsUserAgents.get(ip) + "</span>");
        }
    }

    @Override
    public void destroy() {
        log.info(getServletName() + " destroyed");
    }
}
