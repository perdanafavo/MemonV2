package com.example.projectalpha.Config;

public class ENVIRONMENT {
    //RETROFIT URL
    public static final String BASE_URL = "http://aplikasisto.id/api/";
    public static final String PROFILE_IMAGE = "http://aplikasisto.id/upload/profile/";
    public static final String FOTO_URL = "http://aplikasisto.id/upload/laporan/";

    // VERSION
    public static final String VERSION = "1.1.0";

    //Title UMUM
    public static final String ID_PETUGAS = "ID PETUGAS";
    public static final String ID_LAPORAN = "ID LAPORAN";
    public static final String ID_WITEL = "ID WITEL";
    public static final String ID_STO   = "ID STO";
    public static final String NAMA_WITEL   = "NAMA WITEL";
    public static final String NAMA_STO   = "NAMA STO";
    public static final String DATA_PETUGAS = "DATA PETUGAS";
    public static final String USERNAME_PETUGAS = "USERNAME PETUGAS";
    public static final String RUANGAN = "RUANGAN.jpeg";
    public static final String SUHU = "SUHU.jpeg";
    public static final String TANGGAL_LAPORAN = "TANGGAL LAPORAN";
    public static final String STATUS = "STATUS";
    public static final String STATUS_APPROVED = "STATUS APPROVED";

    //SHIFT SELECTED MESSAGE
    public static final String SHIFT_MALAM_MESSAGE = "Shift malam mulai dari jam 20:00:00 sampai jam 07:59:59";
    public static final String SHIFT_PAGI_MESSAGE = "Shift pagi mulai jam 08:00:00 sampai 19:59:59";

    //FRAGMENT TITLE
    public static final String ROOT_FRAGMENT_TAG = "ROOT FRAGMENT TAG";
    public static final String CREATE_USER_FORM_PERTAMA = "CREATE USER FORM PERTAMA";
    public static final String CREATE_USER_FORM_KEDUA = "CREATE USER FORM KEDUA";

    //Status Title
    public static final String VALIDASI_STATUS = "VALIDASI STATUS";

    public static final String MORNING_STATUS = "MORNING STATUS";
    public static final String NIGHT_STATUS = "NIGHT STATUS";

    public static final String MORNING_TEMPERATURE_STATUS = "MORNING TEMPERATURE STATUS";
    public static final String NIGHT_TEMPERATURE_STATUS = "NIGHT TEMPERATURE STATUS";

    public static final String MORNING_POWER_STATUS = "MORNING POWER STATUS";
    public static final String NIGHT_POWER_STATUS = "NIGHT POWER STATUS";

    public static final String MORNING_FUEL_STATUS = "MORNING FUEL STATUS";
    public static final String NIGHT_FUEL_STATUS = "NIGHT FUEL STATUS";

    //Power Selected
    public static String POWER_SELECTED_ON;
    public static String POWER_SELECTED_OFF;

    //Information Selected
    public static final String PREVILAGE_SELECT = "PREVILAGE SELECTED";
    public static final String SHIFT_SELECTED = "SHIFT SELECTED";
    public static final String INFORMATION_TITLE = "INFORMATION TITLE";
    public static final String TEPERATURE_TITLE = "TEMPERATURE TITLE";
    public static final String FUEL_TITLE = "FUEL TITLE";
    public static final String POWER_TITLE = "POWER TITLE";

    //INTENT REQUEST CODE
    public static final int REQUEST_TAKE_PHOTO = 1;
    public static final int REQUEST_IMAGE_CAPTURE = 2;
    public static final int REQUEST_LOAD_IMG = 3;
    public static final int REQUEST_TAKE_PHOTO_RUANGAN = 4;
    public static final int REQUEST_TAKE_PHOTO_SUHU = 5;

    //ALERT DIALAOG STRING
    public static final String DIALOG_BUTTON_SELESAI = "Selesai";
    public static final String DIALOG_ADD_USERS_TITLE = "Informasi Tambah Users";
    public static final String DIALOG_ADD_USERS_MESSAGE = "Petugas baru telah berhasil ditambahkan";

    //IMAGE SCLAE
    public static final int IMAGE_SCALE_HEIGHT = 240;
    public static final int IMAGE_SCALE_WIDTH = 160;

    //MESSAGE REQUEST
    public static final String ON_FAILURE_REQUEST = "Terjadi gangguan pada server, coba lagi nanti";
    public static final String ON_BAD_REQUEST_GET = "Data tidak ditemukan, coba lagi";
    public static final String ON_BAD_REQUEST_PUT = "Terjadi kesalahan masukan pada form";
    public static final String ON_BAD_REQUEST_IMAGE = "Dimensi atau ukuran bermasalah";
    public static final String ON_BAD_REQUEST_DELETE = "Gagal menghapus petugas, coba lagi";
    public static final String ON_BAD_REQUEST_POST = "Terjadi kesalahan masukan pada form";
    public static final String ON_BAD_REQUEST_VALIDASI = "Masih terdapat kondisi anomali! Segera hubungi rekan PIC terkait";
    public static final String ON_BAD_NULL_VALIDASI = "Masih terdapat data kosong! Segera hubungi rekan PIC terkait";

    //MESSAGE WARNING
    public static final String NO_WAITING_MESSAGE = "Mohon Tunggu";
    public static final String NO_INTERNET_CONNECTION = "Periksa Koneksi Internet Anda!";
    public static final String BACKPRESSED_MESSAGE = "Tekan Back sekali lagi untuk Keluar";
}
