package com.example.assignment_and103_md18306_ph41939;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment_and103_md18306_ph41939.Services.ApiServices;
import com.example.assignment_and103_md18306_ph41939.model.MonAn;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MonAnAdapter extends RecyclerView.Adapter<MonAnAdapter.ViewHolder> {
    private List<MonAn> monAn;
    private Context context;
    TextInputEditText edtTenMon, edtLoaiMon, edtGiaMon, edtMoTa;
    TextInputLayout tilTenMon, tilLoaiMon, tilGiaMon, tilMoTa;

    public MonAnAdapter(Context context, List<MonAn> monAn) {
        this.context = context;
        this.monAn = monAn;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_mon_an, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        MonAn food = monAn.get(position);
        holder.tenLoai.setText(food.getLoaiMon());
        holder.tenMon.setText(food.getTenMon());
        holder.trangThai.setText(food.getTrangThai() == 0 ? "Còn hàng" : "Hết hàng");
        holder.moTa.setText(food.getMoTa());
        holder.giaMon.setText(food.getGiaMon()+"");
//        holder.anhMoTa.setImageURI(monAn.get(position).hienthi(context));
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MonAn monAnCanXoa = monAn.get(position);
                String idMonAn = monAnCanXoa.get_id();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Xóa");
                builder.setMessage("Bạn có chắc chắn muốn xóa món không?");
                builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(ApiServices.link)
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();
                        ApiServices apiService = retrofit.create(ApiServices.class);
                        Call<Void> call = apiService.xoaMonAn(idMonAn);
                        call.enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                if (response.isSuccessful()) {
                                    monAn.remove(position);
                                    // Cập nhật RecyclerView
                                    notifyItemRemoved(position);
                                    Toast.makeText(context, "Xóa món ăn thành công", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(context, "Không thể xóa món ăn", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                Toast.makeText(context, "Lỗi khi xóa món ăn", Toast.LENGTH_SHORT).show();
                            }
                        });
                        notifyDataSetChanged();
                        dialog.dismiss();
                        Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Toast.makeText(context, "Giữ nguyên món ăn!", Toast.LENGTH_SHORT).show();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        holder.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MonAn monAnCanSua = monAn.get(position);
                String idMonAn = monAnCanSua.get_id();
                LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                View view1 = inflater.inflate(R.layout.dialog_them_sua_mon, null);
                builder.setView(view1);
                Dialog dialog = builder.create();
                dialog.show();
                TextView txtTitle = view1.findViewById(R.id.tv_tilte_ma);
                edtTenMon = view1.findViewById(R.id.edtTenMon);
                edtLoaiMon = view1.findViewById(R.id.edtTenLoai);
                edtGiaMon = view1.findViewById(R.id.edtGiaMon);
                edtMoTa = view1.findViewById(R.id.edtMoTa);
                tilTenMon = view1.findViewById(R.id.tilTenMon);
                tilLoaiMon = view1.findViewById(R.id.tilTenLoai);
                tilGiaMon = view1.findViewById(R.id.tilGiaMon);
                tilMoTa = view1.findViewById(R.id.tilMoTa);
                RadioGroup radioGroup = view1.findViewById(R.id.radioGroup);
                RadioButton radioConHang = view1.findViewById(R.id.radioConHang);
                RadioButton radioHetHang = view1.findViewById(R.id.radioHetHang);
                Button btnLayAnh = view1.findViewById(R.id.btnLayAnh);
                Button btnCancelUd = view1.findViewById(R.id.btnCancelThem);
                Button btnSaveUd = view1.findViewById(R.id.btnSaveThem);
                ImageView imgHienThi = view1.findViewById(R.id.imgHienThi);
                txtTitle.setText("Cập nhật món ăn");
                edtTenMon.setText(monAnCanSua.getTenMon());
                edtLoaiMon.setText(monAnCanSua.getLoaiMon());
                edtGiaMon.setText(String.valueOf(monAnCanSua.getGiaMon()));
                edtMoTa.setText(monAnCanSua.getMoTa());

                if (monAnCanSua.getTrangThai() == 0) {
                    radioConHang.setChecked(true);
                } else if (monAnCanSua.getTrangThai() == 1) {
                    radioHetHang.setChecked(true);
                }

                btnCancelUd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        Toast.makeText(context, "Giữ nguyên thông tin món", Toast.LENGTH_SHORT).show();
                    }
                });
                btnSaveUd.setOnClickListener(new View.OnClickListener() {
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

                        MonAn monAn = new MonAn();
                        monAn.setTenMon(tenMon);
                        monAn.setLoaiMon(loaiMon);
                        monAn.setGiaMon(Double.parseDouble(giaMon));
                        monAn.setMoTa(moTa);
                        monAn.setTrangThai(trangThai.equals("Còn hàng") ? 1 : 0);
                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(ApiServices.link)
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();
                        ApiServices apiService = retrofit.create(ApiServices.class);
                        Call<MonAn> call = apiService.capNhatMonAn(idMonAn, monAn);
                        call.enqueue(new Callback<MonAn>() {
                            @Override
                            public void onResponse(Call<MonAn> call, Response<MonAn> response) {
                                if (response.isSuccessful()){
                                    monAnCanSua.setTenMon(monAn.getTenMon());
                                    monAnCanSua.setLoaiMon(monAn.getLoaiMon());
                                    monAnCanSua.setGiaMon(monAn.getGiaMon());
                                    monAnCanSua.setMoTa(monAn.getMoTa());
                                    monAnCanSua.setTrangThai(monAn.getTrangThai());
                                    notifyItemChanged(position);
                                    dialog.dismiss();
                                    Toast.makeText(context, "Cập nhật món ăn thành công", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(context, "Không thể cập nhật món ăn", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<MonAn> call, Throwable t) {
                                Toast.makeText(context, "Lỗi khi cập nhật món ăn", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return monAn.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tenLoai, tenMon, giaMon, trangThai, moTa;
        ImageView anhMoTa;
        ImageButton btnUpdate, btnDelete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tenLoai = itemView.findViewById(R.id.lblDanhMuc);
            tenMon = itemView.findViewById(R.id.lblTenMon);
            giaMon = itemView.findViewById(R.id.lblGia);
            trangThai = itemView.findViewById(R.id.lblTrangThai);
            moTa = itemView.findViewById(R.id.lblMoTa);
            anhMoTa = itemView.findViewById(R.id.imgMonAn);
            btnDelete = itemView.findViewById(R.id.btnXoaMon);
            btnUpdate = itemView.findViewById(R.id.btnCapNhat);
        }
    }
}
