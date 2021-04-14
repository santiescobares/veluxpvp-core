package club.veluxpvp.core;

public final class Settings {

	public static final String DATABASE_HOST = Core.getInstance().getConfig().getString("DATABASE.HOST");
	public static final String DATABASE_NAME = Core.getInstance().getConfig().getString("DATABASE.NAME");
	public static final String DATABASE_USERNAME = Core.getInstance().getConfig().getString("DATABASE.AUTH.USERNAME");
	public static final String DATABASE_PASSWORD = Core.getInstance().getConfig().getString("DATABASE.AUTH.PASSWORD");
	public static final int DATABASE_PORT = Core.getInstance().getConfig().getInt("DATABASE.PORT");
	public static final String TABLE_PREFIX = Core.getInstance().getConfig().getString("DATABASE.TABLE_PREFIX");
	
	public static final String PROFILES_TABLE = TABLE_PREFIX + "profiles";
	public static final String GRANTS_TABLE = TABLE_PREFIX + "grants";
	public static final String PUNISHMENTS_TABLE = TABLE_PREFIX + "punishments";
	public static final String SYNC_TABLE = TABLE_PREFIX + "sync";
}
