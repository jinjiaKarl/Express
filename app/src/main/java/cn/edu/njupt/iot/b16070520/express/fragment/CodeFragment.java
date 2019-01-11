package cn.edu.njupt.iot.b16070520.express.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.WriterException;

import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.yzq.zxinglibrary.android.CaptureActivity;
import com.yzq.zxinglibrary.common.Constant;
import com.yzq.zxinglibrary.encode.CodeCreator;

import java.util.List;

import cn.edu.njupt.iot.b16070520.express.R;

public class CodeFragment  extends Fragment implements View.OnClickListener {

    private Button scanBtn;
    private EditText result;
    private EditText contentEt;
    private Button encodeBtn;
    private ImageView contentIv;
    private int REQUEST_CODE_SCAN = 111;
    /**
     * 生成带logo的二维码
     */
    private Button encodeBtnWithLogo;
    private ImageView contentIvWithLogo;
    private String contentEtString;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_code, null);
        initView(view);
        return view;
    }


    private void initView(View view) {
        /*扫描按钮*/
        scanBtn = view.findViewById(R.id.scanBtn);
        scanBtn.setOnClickListener(this);
        /*扫描结果*/
        result = view.findViewById(R.id.result);

        /*要生成二维码的输入框*/
        contentEt = view.findViewById(R.id.contentEt);
        /*生成按钮*/
        encodeBtn = view.findViewById(R.id.encodeBtn);
        encodeBtn.setOnClickListener(this);
        /*生成的图片*/
        contentIv = view.findViewById(R.id.contentIv);


        result = (EditText) view.findViewById(R.id.result);
        scanBtn = (Button) view.findViewById(R.id.scanBtn);
        contentEt = (EditText) view.findViewById(R.id.contentEt);
        encodeBtnWithLogo = (Button) view.findViewById(R.id.encodeBtnWithLogo);
        encodeBtnWithLogo.setOnClickListener(this);
        contentIvWithLogo = (ImageView) view.findViewById(R.id.contentIvWithLogo);
        encodeBtn = (Button) view.findViewById(R.id.encodeBtn);
        contentIv = (ImageView) view.findViewById(R.id.contentIv);
    }

    @Override
    public void onClick(View v) {

        Bitmap bitmap = null;
        switch (v.getId()) {
            case R.id.scanBtn:
                AndPermission.with(this)

                        .permission(Permission.CAMERA, Permission.READ_EXTERNAL_STORAGE)
                        .onGranted(new Action() {
                            @Override
                            public void onAction(List<String> permissions) {
                                Intent intent = new Intent(getActivity(), CaptureActivity.class);
                                /*ZxingConfig是配置类
                                 *可以设置是否显示底部布局，闪光灯，相册，
                                 * 是否播放提示音  震动
                                 * 设置扫描框颜色等
                                 * 也可以不传这个参数
                                 * */
//                                ZxingConfig config = new ZxingConfig();
//                                config.setPlayBeep(false);//是否播放扫描声音 默认为true
//                                config.setShake(false);//是否震动  默认为true
//                                config.setDecodeBarCode(false);//是否扫描条形码 默认为true
//                                config.setReactColor(R.color.colorAccent);//设置扫描框四个角的颜色 默认为白色
//                                config.setFrameLineColor(R.color.colorAccent);//设置扫描框边框颜色 默认无色
//                                config.setScanLineColor(R.color.colorAccent);//设置扫描线的颜色 默认白色
//                                config.setFullScreenScan(false);//是否全屏扫描  默认为true  设为false则只会在扫描框中扫描
//                                intent.putExtra(Constant.INTENT_ZXING_CONFIG, config);
                                startActivityForResult(intent, REQUEST_CODE_SCAN);
                            }
                        })
                        .onDenied(new Action() {
                            @Override
                            public void onAction(List<String> permissions) {
                                Uri packageURI = Uri.parse("package:" + getActivity().getPackageName());
                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageURI);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                                startActivity(intent);

                                Toast.makeText(getActivity(), "没有权限无法扫描呦", Toast.LENGTH_LONG).show();
                            }
                        }).start();

                break;
            case R.id.encodeBtn:
                contentEtString = contentEt.getText().toString().trim();
                if (TextUtils.isEmpty(contentEtString)) {
                    Toast.makeText(getActivity(), "请输入要生成二维码图片的字符串", Toast.LENGTH_SHORT).show();
                    return;
                }


                try {
                    bitmap = CodeCreator.createQRCode(contentEtString, 400, 400, null);

                } catch (WriterException e) {
                    e.printStackTrace();
                }
                if (bitmap != null) {
                    contentIv.setImageBitmap(bitmap);
                }

                break;

            case R.id.encodeBtnWithLogo:

                contentEtString = contentEt.getText().toString().trim();
                if (TextUtils.isEmpty(contentEtString)) {
                    Toast.makeText(getActivity(), "请输入要生成二维码图片的字符串", Toast.LENGTH_SHORT).show();
                    return;
                }

                bitmap = null;
                try {
                    Bitmap logo = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
                    bitmap = CodeCreator.createQRCode(contentEtString, 400, 400, logo);

                } catch (WriterException e) {
                    e.printStackTrace();
                }
                if (bitmap != null) {
                    contentIvWithLogo.setImageBitmap(bitmap);
                }

                break;

            default:
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // 扫描二维码/条码回传
        if (requestCode == REQUEST_CODE_SCAN && resultCode == getActivity().RESULT_OK) {
            if (data != null) {

                String content = data.getStringExtra(Constant.CODED_CONTENT);
                result.setText("扫描结果为：" + content);
            }
        }
    }

}
