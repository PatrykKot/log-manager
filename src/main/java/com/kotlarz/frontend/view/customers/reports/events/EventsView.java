package com.kotlarz.frontend.view.customers.reports.events;

import com.kotlarz.frontend.presenter.customers.reports.events.EventsPresenter;
import com.vaadin.navigator.View;
import com.vaadin.server.FileDownloader;
import com.vaadin.server.StreamResource;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

@SpringComponent
@UIScope
public class EventsView extends EventsViewDesign implements View {
    @Autowired
    private EventsPresenter eventsPresenter;

    private FileDownloader downloader;

    private StreamResource streamResource;

    @PostConstruct
    private void init() {
        streamResource = new StreamResource((StreamResource.StreamSource) () -> new ByteArrayInputStream(eventsTextArea.getValue().getBytes(StandardCharsets.UTF_8)), "log.log");
        downloader = new FileDownloader(streamResource);
        downloader.extend(downloadLogFileButton);

        eventsPresenter.initView(this);
    }

    public void setLog(String log) {
        eventsTextArea.setValue(log);
    }

    public void setDownloadFilename(String filename) {
        streamResource.setFilename(filename);
    }

    public void addOnFiltersButtonClick(Button.ClickListener listener) {
        filtersButton.addClickListener(listener);
    }
}
