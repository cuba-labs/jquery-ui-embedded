package com.company.jqueryembed.web.screens;

import com.haulmont.cuba.gui.components.Embedded;
import com.haulmont.cuba.gui.components.Embedded.Type;
import com.haulmont.cuba.web.app.mainwindow.AppMainWindow;
import com.haulmont.cuba.web.controllers.ControllerUtils;
import org.apache.commons.io.IOUtils;

import javax.inject.Inject;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.UUID;

public class ExtAppMainWindow extends AppMainWindow {
    @Inject
    private Embedded embedded;

    @Override
    public void init(Map<String, Object> params) {
        super.init(params);

        byte[] htmlContent;
        try {
            // read from resource, we can generate it on-the-fly
            byte[] resourceContent = IOUtils.toByteArray(getClass()
                    .getResourceAsStream("/com/company/jqueryembed/web/screens/my-calendar.html"));
            String html = new String(resourceContent, StandardCharsets.UTF_8);
            html = html.replace("<app-url>", ControllerUtils.getLocationWithoutParams());
            htmlContent = html.getBytes(StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Unable to read html");
        }
        embedded.setSource("my-calendar-" + UUID.randomUUID() + ".html",
                new ByteArrayInputStream(htmlContent));
        embedded.setType(Type.BROWSER);
    }
}