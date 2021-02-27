export class AppConstants {
  private static API_BASE_URL = "http://localhost:8080/StudentOne/";
  private static OAUTH2_URL = AppConstants.API_BASE_URL + "authenticateservice/oauth2/authorization/";
  private static REDIRECT_URL = "?redirect_uri=http://localhost:4200/login";
  public static API_URL = AppConstants.API_BASE_URL + "authenticateservice/api/";
  public static AUTH_API = AppConstants.API_URL + "auth/";
  public static GOOGLE_AUTH_URL = AppConstants.OAUTH2_URL + "google" + AppConstants.REDIRECT_URL;

  public static GETALLEVENT = "http://localhost:8080/StudentOne/calendarservice/api/calendar/getAllEvents"
}

export class SocketCostants{
  private static API_BASE_URL = "http://localhost:8083";

  private static API_CHAT = SocketCostants.API_BASE_URL + "/api/chat"

  private static API_USER = SocketCostants.API_BASE_URL + "/api/user"

  public static SOCKET_URL = SocketCostants.API_BASE_URL + "/socket"

  public static GETTUSERS = SocketCostants.API_USER + "/getAllUsers"

  public static GETCHATS = SocketCostants. API_CHAT + "/getAllChats"

  public static GETCHATBYID = SocketCostants. API_CHAT + "/getChat"

}
