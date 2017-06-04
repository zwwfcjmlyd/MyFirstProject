package com.zww.hichat.view.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.fourmob.datetimepicker.date.DatePickerDialog;
import com.lljjcoder.citypickerview.widget.CityPicker;
import com.sleepbot.datetimepicker.time.RadialPickerLayout;
import com.sleepbot.datetimepicker.time.TimePickerDialog;
import com.zww.hichat.R;
import com.zww.hichat.base.BaseActivity;
import com.zww.hichat.utils.MyToast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Calendar;

import butterknife.ButterKnife;

public class PersonalInfoActivity extends BaseActivity implements View.OnClickListener {
    private static final int PERSONAL_PIC = 100;
    ImageView ivIcon;
    TextView tvNickname;
    TextView tvBirthday;
    TextView tvHome;
    RadioButton rbMale;
    RadioButton rbFemale;
    Button btNext;
    private String mNikename;
    private DatePickerDialog mDatePickerDialog;
    private TimePickerDialog mTimePickerDialog;

    @Override
    public View addBodyView(LayoutInflater inflater, FrameLayout content) {
        View view = inflater.inflate(R.layout.activity_personal_info, content, true);
        return view;
    }

    @Override
    protected void initContantView(View view) {
        ivIcon = ButterKnife.findById(view, R.id.iv_icon);
        tvNickname = ButterKnife.findById(view, R.id.tv_nickname);
        tvBirthday = ButterKnife.findById(view, R.id.tv_birthday);
        tvHome = ButterKnife.findById(view, R.id.tv_home);
        rbMale = ButterKnife.findById(view, R.id.rb_male);
        rbFemale = ButterKnife.findById(view, R.id.rb_female);
        btNext = ButterKnife.findById(view, R.id.bt_next);

        btNext.setOnClickListener(this);
        ivIcon.setOnClickListener(this);
        tvHome.setOnClickListener(this);
        tvBirthday.setOnClickListener(this);
        rbMale.setOnClickListener(this);
        rbFemale.setOnClickListener(this);

        //将传递过来的昵称进行显示
        mNikename = getIntent().getStringExtra("username");
        tvNickname.setText(mNikename);
    }

    public void onClick(View view) {
        super.onClick(view);//让父类的onClick()得到调用
        switch (view.getId()) {
            case R.id.iv_icon:
                pickPictureFromSystemGallery();
                break;
            case R.id.tv_home:
                showSelectHomeDialog();
                break;
            case R.id.tv_birthday:
                showSelectDateDialog();
                break;
            case R.id.rb_male:
                changeSexCheckState(true);
                break;
            case R.id.rb_female:
                changeSexCheckState(false);
                break;
            case R.id.bt_next:
                showSexConfirmAlertDialog();
                break;
        }
    }

    /**
     * 检查下一步按钮是否可点击,当所有选项都选择以后才可以点击
     * @return
     */
    private boolean changeNextButtonState() {
        if (ivIcon.getDrawable() == null) {
            return false;
        }
        String ads = tvHome.getText().toString().trim();
        if (TextUtils.isEmpty(ads)) {
            return false;
        }
        String birthday = tvBirthday.getText().toString().trim();
        if (TextUtils.isEmpty(birthday)) {
            return false;
        }
        if (!rbFemale.isChecked() && !rbMale.isChecked()) {
            return false;
        }
        return true;
    }

    /**
     * 从系统相册中获取图片
     */
    private void pickPictureFromSystemGallery() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, PERSONAL_PIC);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PERSONAL_PIC:
                if (data != null) {
                    //获取拿到的图片位置
                    Uri uri = data.getData();
                    if (uri != null) {
                        crop(uri);
                    }
                }
                break;
            case 200:
                Bitmap bitmap = data.getParcelableExtra("data");
                if (bitmap != null) {
                    //显示图片
                    ivIcon.setImageBitmap(bitmap);
                    //更新下一步按钮的状态
                    btNext.setEnabled(changeNextButtonState());

                    //保存图片
                    try {
                        FileOutputStream fileOutputStream = openFileOutput(mNikename + ".jpg", Context.MODE_PRIVATE);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }

    /**
     * 剪切图片
     */
    private void crop(Uri uri) {
        // 裁剪图片意图
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // 裁剪框的比例，1：1
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // 裁剪后输出图片的尺寸大小
        intent.putExtra("outputX", 250);
        intent.putExtra("outputY", 250);

        intent.putExtra("outputFormat", "JPEG");// 图片格式
        intent.putExtra("noFaceDetection", true);// 取消人脸识别
        intent.putExtra("return-data", true);
        // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_CUT
        startActivityForResult(intent, 200);
    }

    /**
     * 地址选择的三级联动
     */
    private void showSelectHomeDialog() {
        CityPicker cityPicker = new CityPicker.Builder(this)
                .textSize(20)
                .title("地址选择")
                .backgroundPop(0xa0000000)
                .titleBackgroundColor("#234Dfa")
                .titleTextColor("#000000")
                .backgroundPop(0xa0000000)
                .confirTextColor("#000000")
                .cancelTextColor("#000000")
                .province("浙江省")
                .city("杭州市")
                .district("萧山区")
                .textColor(Color.parseColor("#000000"))
                .provinceCyclic(true)
                .cityCyclic(false)
                .districtCyclic(false)
                .visibleItemsCount(7)
                .itemPadding(10)
                .onlyShowProvinceAndCity(false)
                .build();
        cityPicker.show();

        //监听方法，获取选择结果
        cityPicker.setOnCityItemClickListener(new CityPicker.OnCityItemClickListener() {
            @Override
            public void onSelected(String... citySelected) {
                //省份
                String province = citySelected[0];
                //城市
                String city = citySelected[1];
                //区县（如果设定了两级联动，那么该项返回空）
                String district = citySelected[2];
                tvHome.setText(province + "-" + city + "-" + district);

                //更新下一步按钮的状态
                btNext.setEnabled(changeNextButtonState());
            }

            @Override
            public void onCancel() {
                MyToast.show(PersonalInfoActivity.this, "已取消");
            }
        });
    }

    /**
     * 弹出选择日期的对话框
     */
    private void showSelectDateDialog() {
        if (mDatePickerDialog == null && mTimePickerDialog == null) {
            Calendar calendar = Calendar.getInstance();
            //日期
            mDatePickerDialog = DatePickerDialog.newInstance(new MyDateAndTimeListener(),
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH),
                    false);
            //设置区间
            mDatePickerDialog.setYearRange(2000, 2017);

            //时间
            mTimePickerDialog = TimePickerDialog.newInstance(new MyDateAndTimeListener(),
                    calendar.get(Calendar.HOUR_OF_DAY),
                    calendar.get(Calendar.MINUTE),
                    true,
                    false);

        }
        mDatePickerDialog.show(getSupportFragmentManager(), "date");
        mTimePickerDialog.show(getSupportFragmentManager(), "time");

    }

    //选择完日期时间后回调的方法
    class MyDateAndTimeListener implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {


        @Override
        public void onDateSet(DatePickerDialog datePickerDialog, int year, int month, int day) {
            tvBirthday.setText(year + "-" + month + "-" + day);

            //更新下一步按钮的状态
            btNext.setEnabled(changeNextButtonState());
        }

        @Override
        public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
            tvBirthday.setText(tvBirthday.getText().toString().trim() + " " + hourOfDay + "-" + minute);
        }
    }

    /**
     * 选择性别为male
     *
     * @param b
     */
    private void changeSexCheckState(boolean b) {
        rbMale.setChecked(b);
        rbFemale.setChecked(!b);
        //更新下一步按钮的状态
        btNext.setEnabled(changeNextButtonState());
    }

    /**
     * 性别确认的提示框
     */
    private void showSexConfirmAlertDialog() {
        new AlertDialog.Builder(this)
                .setTitle("提示")
                .setMessage("注册成功后性别不可以修改")
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //进入注册界面
                        startActivity(new Intent(PersonalInfoActivity.this,RegisterActivity.class));
                    }
                })
                .setNegativeButton("取消",null)
                .show();
    }


}
