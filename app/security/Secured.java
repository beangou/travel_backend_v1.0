package security;

import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;

/**
 * Created by beangou on 16/7/5.
 */
public class Secured extends Security.Authenticator {

    @Override
    public String getUsername(Http.Context ctx) {
        return ctx.session().get("username");
    }

    @Override
    public Result onUnauthorized(Http.Context ctx) {
        return unauthorized();
    }
}