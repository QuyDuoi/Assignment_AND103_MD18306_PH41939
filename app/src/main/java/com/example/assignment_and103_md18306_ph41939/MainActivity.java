package com.example.assignment_and103_md18306_ph41939;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.assignment_and103_md18306_ph41939.Services.ApiServices;
import com.example.assignment_and103_md18306_ph41939.model.MonAn;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.annotation.Nullable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rcv;
    private MonAnAdapter monAnAdapter;
    ArrayList<MonAn> list = new ArrayList<>();
    TextInputEditText edtTenMon, edtLoaiMon, edtGiaMon, edtMoTa, edtSearch;
    TextInputLayout tilTenMon, tilLoaiMon, tilGiaMon, tilMoTa, tilSearch;
    ImageButton btnThem;
    ImageView imgHienThi;
    Uri selectedImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rcv = findViewById(R.id.rcvFood);
        btnThem = findViewById(R.id.btnAdd);
        edtSearch = findViewById(R.id.edtSearch);
        tilSearch = findViewById(R.id.tilSearch);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiServices.link)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiServices apiService = retrofit.create(ApiServices.class);
        Call<ArrayList<MonAn>> monAn = apiService.layDanhSachMon();
        monAn.enqueue(new Callback<ArrayList<MonAn>>() {
            @Override
            public void onResponse(Call<ArrayList<MonAn>> call, Response<ArrayList<MonAn>> response) {
                if (response.isSuccessful()) {
                    list = response.body();

                    monAnAdapter = new MonAnAdapter(MainActivity.this, list);
                    rcv.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                    rcv.setAdapter(monAnAdapter);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<MonAn>> call, Throwable t) {
                Log.e("Main", t.getMessage());
            }
        });
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                themMonAn();
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            // Lấy Uri của ảnh từ Intent
            selectedImageUri = data.getData();
            try {
                // Chuyển đổi định dạng ảnh sang dạng Base64
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] imageBytes = baos.toByteArray();
                String base64Image = Base64.encodeToString(imageBytes, Base64.DEFAULT);

                // Hiển thị ảnh trên ImageView
                imgHienThi.setImageBitmap(bitmap);

                // Lưu dữ liệu ảnh dưới dạng Base64 vào biến selectedImageUri
                selectedImageUri = Uri.parse(base64Image);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private void themMonAn() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_them_sua_mon, null);
        builder.setView(dialogView);
        builder.setCancelable(false);
        final AlertDialog dialog = builder.create();
        edtTenMon = dialogView.findViewById(R.id.edtTenMon);
        edtLoaiMon = dialogView.findViewById(R.id.edtTenLoai);
        edtGiaMon = dialogView.findViewById(R.id.edtGiaMon);
        edtMoTa = dialogView.findViewById(R.id.edtMoTa);
        tilTenMon = dialogView.findViewById(R.id.tilTenMon);
        tilLoaiMon = dialogView.findViewById(R.id.tilTenLoai);
        tilGiaMon = dialogView.findViewById(R.id.tilGiaMon);
        tilMoTa = dialogView.findViewById(R.id.tilMoTa);
        RadioGroup radioGroup = dialogView.findViewById(R.id.radioGroup);
        RadioButton radioConHang = dialogView.findViewById(R.id.radioConHang);
        RadioButton radioHetHang = dialogView.findViewById(R.id.radioHetHang);
        Button btnLayAnh = dialogView.findViewById(R.id.btnLayAnh);
        Button btnCancelThem = dialogView.findViewById(R.id.btnCancelThem);
        Button btnSaveThem = dialogView.findViewById(R.id.btnSaveThem);
        imgHienThi = dialogView.findViewById(R.id.imgHienThi);
        radioConHang.setChecked(true);
        btnLayAnh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, 1);
            }
        });

        btnSaveThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedId = radioGroup.getCheckedRadioButtonId();
                String trangThai = "";
                if (selectedId == R.id.radioConHang) {
                    trangThai = "Còn hàng";
                } else if (selectedId == R.id.radioHetHang) {
                    trangThai = "Hết hàng";
                }
                String tenMon = edtTenMon.getText().toString();
                String loaiMon = edtLoaiMon.getText().toString();
                String giaMon = edtGiaMon.getText().toString();
                String moTa = edtMoTa.getText().toString();

                boolean check = true;

                if (TextUtils.isEmpty(tenMon)) {
                    tilTenMon.setError("Vui lòng nhập tên món");
                    check = false;
                } else {
                    tilTenMon.setError(null);
                }

                if (TextUtils.isEmpty(loaiMon)) {
                    tilLoaiMon.setError("Vui lòng nhập loại món");
                    check = false;
                } else {
                    tilLoaiMon.setError(null);
                }

                if (TextUtils.isEmpty(giaMon)) {
                    tilGiaMon.setError("Vui lòng nhập giá món");
                    check = false;
                } else {
                    tilGiaMon.setError(null);
                }

                if (TextUtils.isEmpty(moTa)) {
                    tilMoTa.setError("Vui lòng nhập mô tả món");
                    check = false;
                } else {
                    tilMoTa.setError(null);
                }

                if (!check) {
                    return;
                }

                byte[] imageData = convertImageToByteArray(selectedImageUri);

                // Tạo đối tượng MonAn và gán dữ liệu
                MonAn monAn = new MonAn();
                monAn.setTenMon(tenMon);
                monAn.setLoaiMon(loaiMon);
                monAn.setGiaMon(Double.parseDouble(giaMon));
                monAn.setMoTa(moTa);
                monAn.setTrangThai(trangThai.equals("Còn hàng") ? 0 : 1);
                monAn.setAnhMon(imageData);
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(ApiServices.link)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                ApiServices apiService = retrofit.create(ApiServices.class);
                Call<MonAn> call = apiService.themMon(monAn);
                call.enqueue(new Callback<MonAn>() {
                    @Override
                    public void onResponse(Call<MonAn> call, Response<MonAn> response) {
                        if (response.isSuccessful()){
                            MonAn newMonAn = response.body();
                            list.add(monAn);
                            monAnAdapter.notifyDataSetChanged();
                            dialog.dismiss();
                            Toast.makeText(MainActivity.this, "Thêm món ăn thành công", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<MonAn> call, Throwable t) {

                    }
                });
            }
        });
        btnCancelThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                Toast.makeText(MainActivity.this, "Hủy thao tác!", Toast.LENGTH_SHORT).show();
            }
        });
        dialog.show();
    }
    private byte[] convertImageToByteArray(Uri imageUri) {
        byte[] imageData = null;
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            imageData = baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageData;
    }
}
