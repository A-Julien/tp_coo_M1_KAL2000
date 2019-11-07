package Ui;

import KAL2000.Kal2000;

public interface Logging {

    void connect(int id, Kal2000 kal2000);

    void connect(int id, String password, Kal2000 kal2000);

    void disconnect();
}
