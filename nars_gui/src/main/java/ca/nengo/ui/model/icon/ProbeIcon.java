/*
The contents of this file are subject to the Mozilla Public License Version 1.1 
(the "License"); you may not use this file except in compliance with the License. 
You may obtain a copy of the License at http://www.mozilla.org/MPL/

Software distributed under the License is distributed on an "AS IS" basis, WITHOUT
WARRANTY OF ANY KIND, either express or implied. See the License for the specific 
language governing rights and limitations under the License.

The Original Code is "ProbeIcon.java". Description: 
"Icon for a Simulator Probe
  
  @author Shu Wu"

The Initial Developer of the Original Code is Bryan Tripp & Centre for Theoretical Neuroscience, University of Waterloo. Copyright (C) 2006-2008. All Rights Reserved.

Alternatively, the contents of this file may be used under the terms of the GNU 
Public License license (the GPL License), in which case the provisions of GPL 
License are applicable  instead of those above. If you wish to allow use of your 
version of this file only under the terms of the GPL License and not to allow 
others to use your version of this file under the MPL, indicate your decision 
by deleting the provisions above and replace  them with the notice and other 
provisions required by the GPL License.  If you do not delete the provisions above,
a recipient may use your version of this file under either the MPL or the GPL License.
*/

package ca.nengo.ui.model.icon;

import ca.nengo.ui.lib.NengoStyle;
import ca.nengo.ui.lib.object.model.ModelObject;
import ca.nengo.ui.lib.world.piccolo.WorldObjectImpl;
import ca.nengo.ui.lib.world.piccolo.primitive.PXPath;

import java.awt.*;

/**
 * Icon for a Simulator Probe
 * 
 * @author Shu Wu
 */
public class ProbeIcon extends ModelIcon {

	public static Color DEFAULT_COLOR = NengoStyle.COLOR_LIGHT_PURPLE;

	public ProbeIcon(ModelObject parent) {
		super(parent, new WorldObjectImpl(new Triangle()));

	}

	public void setColor(Color color) {
		getIconReal().setPaint(color);
	}

}

/**
 * Icon which is basically a right-facing equilateral triangle
 * 
 * @author Shu Wu
 */
class Triangle extends PXPath {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	static final double probeIconSize = 20;

	public Triangle() {
		super();

		float x = 0;
		float y = 0;

		moveTo(x, y);

		x -= probeIconSize * Math.cos(Math.PI / 6);
		y -= probeIconSize * Math.sin(Math.PI / 6);

		lineTo(x, y);

		y += probeIconSize;

		lineTo(x, y);
		closePath();

		setPaint(NengoStyle.COLOR_LIGHT_PURPLE);

	}

}
