package com.fireflyest.gui.api;

public interface View<T extends ViewPage> {

    T getFirstPage(String target);

    void removePage(String target);

}
