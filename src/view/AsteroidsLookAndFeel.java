/**
 *	@author Ariana Fairbanks
 */

package view;

import javax.swing.UIDefaults;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

public class AsteroidsLookAndFeel extends NimbusLookAndFeel
{
	protected void initClassDefaults(UIDefaults table)
	{
		super.initClassDefaults(table);
	 	table.put("ButtonUI","package1.package2.MyButtonUI");
	}
}
