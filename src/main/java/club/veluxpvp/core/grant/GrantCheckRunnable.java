package club.veluxpvp.core.grant;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.scheduler.BukkitRunnable;

import club.veluxpvp.core.Core;

public class GrantCheckRunnable extends BukkitRunnable {

	@Override
	public void run() {
		GrantManager grantManager = Core.getInstance().getGrantManager();
		List<Grant> activeGrants = grantManager.getAllGrants().stream().filter(g -> g.isActive()).collect(Collectors.toList());

		for(Grant g : activeGrants) {
			if(g.isPermanent()) continue;
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			
			try {
				Date expiresOn = sdf.parse(g.getExpiresOn());
				Date now = new Date();

				if(now.after(expiresOn)) {
					g.remove();
					grantManager.saveAsync(g);
				}
			} catch(ParseException e) {
				e.printStackTrace();
			}
		}
	}
}
