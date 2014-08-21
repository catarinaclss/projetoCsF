package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;

public class Application extends Controller {

    public static Result index() {
		if (session().get("user") == null) {
			return redirect(routes.Login.show());
		}
        return ok(index.render("Home Page"));
    }

}
