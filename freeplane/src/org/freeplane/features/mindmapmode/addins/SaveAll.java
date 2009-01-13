/*
 *  Freeplane - mind map editor
 *  Copyright (C) 2008 Joerg Mueller, Daniel Polansky, Christian Foltin, Dimitry Polivaev
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 2 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.freeplane.features.mindmapmode.addins;

import java.awt.event.ActionEvent;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JOptionPane;

import org.freeplane.core.controller.Controller;
import org.freeplane.core.model.MapModel;
import org.freeplane.core.ui.ActionDescriptor;
import org.freeplane.core.ui.FreeplaneAction;
import org.freeplane.features.mindmapmode.MModeController;

/**
 * @author foltin
 */
@ActionDescriptor(tooltip = "accessories/plugins/SaveAll.properties_documentation", //
name = "accessories/plugins/SaveAll.properties_name", //
locations = { "/menu_bar/file/open" })
public class SaveAll extends FreeplaneAction {
	/**
	 *
	 */
	public SaveAll() {
		super();
	}

	public void actionPerformed(final ActionEvent e) {
		final Controller mainController = Controller.getController();
		final MapModel initialMapView = mainController.getMap();
		final Map<String, MapModel> mapViews = getMapViews();
		final Iterator<Entry<String, MapModel>> iterator = mapViews.entrySet().iterator();
		while (iterator.hasNext()) {
			final Entry<String, MapModel> entry = iterator.next();
			final MapModel mapView = entry.getValue();
			mainController.getMapViewManager().changeToMapView(entry.getKey());
			if (!((MModeController) mapView.getModeController()).save()) {
				JOptionPane.showMessageDialog(Controller.getController().getViewController()
				    .getContentPane(), "Freeplane", Controller
				    .getText("accessories/plugins/SaveAll.properties_save_all_cancelled"),
				    JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
		mainController.getMapViewManager().changeToMapView(initialMapView.toString());
	}

	/**
	 */
	private Map<String, MapModel> getMapViews() {
		return Controller.getController().getMapViewManager().getMaps();
	}
}
