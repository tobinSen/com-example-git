package com.example.spring.mpush.mpush;

import com.mpush.api.Constants;
import com.mpush.api.push.*;
import com.mpush.api.router.ClientLocation;
import com.mpush.tools.Jsons;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class DiscoveryPushMessageService {
    public static final Logger LOGGER = LoggerFactory.getLogger(DiscoveryPushMessageService.class);

    private final AtomicLong msgIdSeq = new AtomicLong(1L);

    @Autowired
    private PushSender mpusher;

    @Value("${mps.environment}")
    private String environment;

    @Value("${mps.apple.isSandbox}")
    private boolean isSandbox;

    private static String certificatePath;

    @PostConstruct
    public void init() {
        try {
            ApplicationHome appHome = new ApplicationHome(getClass());
            File homeFile = appHome.getDir();
            File jarFile = null;
            if (homeFile.getAbsolutePath().endsWith(".jar")) {
                jarFile = new File(homeFile.getParentFile().getParentFile().getAbsolutePath() + File.separator + "certificate" + File.separator);
                if (!jarFile.exists())
                    jarFile = new File(homeFile.getParentFile().getParentFile().getAbsolutePath() + File.separator + "classes" + File.separator + "certificate" + File.separator + this.environment + File.separator);
            }
            if (jarFile == null || !jarFile.exists())
                jarFile = new File(homeFile.getParentFile().getAbsolutePath() + File.separator + "certificate" + File.separator);
            if (!jarFile.exists())
                jarFile = new File(homeFile.getAbsolutePath() + File.separator + "certificate" + File.separator + this.environment + File.separator);
            if (jarFile.exists())
                certificatePath = jarFile.getAbsolutePath() + File.separator;
            LOGGER.info("DiscoveryPushMessageService#init ,homeFile.getAbsolutePath()={},homeFileParentPath={}, certificatePath={}", homeFile.getAbsolutePath(), homeFile.getParentFile().getAbsolutePath(), certificatePath);
        } catch (Exception e) {
            LOGGER.error("APNs", e);
        }
    }

    public boolean push2APNs(String deviceToken, PustMessageDTO dto) {
        //push2ANPsDirectly(deviceToken, dto);
        return true;
    }

    public boolean send2MPush(final PustMessageDTO dto, final List<String> userIdList) {
        PushMsg pushMsg = PushMsg.build(MsgType.NOTIFICATION_AND_MESSAGE, Jsons.toJson(dto));
        pushMsg.setMsgId(Long.toString(this.msgIdSeq.incrementAndGet()));
        final byte[] content = Jsons.toJson(dto).getBytes(Constants.UTF_8);
        // 1、推送的上下文(携带内容)
        PushContext context = new PushContext(content);
        if (userIdList == null) {
            context.setBroadcast(true); //广播发送
        } else if (userIdList.size() == 1) {
            context.setUserId(userIdList.get(0)); //单播发送
        } else if (userIdList.size() > 1) {
            context.setUserIds(userIdList); //组播发送
        }
        // 2、设置回调地址
        context.setCallback(new PushCallback() {
            int retryCount = 0;

            //发送成功
            public void onSuccess(String userId, ClientLocation location) {

            }
            //发送失败
            public void onFailure(String userId, ClientLocation location) {
            }
            //用户下线
            public void onOffline(String userId, ClientLocation location) {
                if (location != null) {
                    String os = location.getOsName().toLowerCase();
                    if (os.contains("ios")) {
                        String[] partUid = StringUtils.split(userId, "\\$");
                        if (partUid.length != 3)
                            return;
                        DiscoveryPushMessageService.this.send2ANPs(userId, location.getDeviceId(), dto);
                    } else if (os.contains("android")) {
                        if (os.contains("xiaomi")) {
                            //DiscoveryPushMessageService.this.send2MiPush(dto, userId);
                        } else if (os.contains("huawei")) {
                            DiscoveryPushMessageService.this.send2HuaweiPush(dto, userId);
                        } else {
                            DiscoveryPushMessageService.this.send2JPush(dto, userId);
                        }
                    } else {
                       // DiscoveryPushMessageService.this.saveOfflineMsg(new OfflineMsg(userId, content));
                    }
                } else {
                   // DiscoveryPushMessageService.this.saveOfflineMsg(new OfflineMsg(userId, content));
                }
            }

            //用户连接超时
            public void onTimeout(String userId, ClientLocation location) {
                if (this.retryCount++ > 1) {
                    //DiscoveryPushMessageService.this.saveOfflineMsg(new OfflineMsg(userId, content));
                } else {
                    DiscoveryPushMessageService.this.send2MPush(dto, userIdList);
                }
            }
        }); //设置回调
        // 3、发送消息
        this.mpusher.send(context);
        return true;
    }

    public boolean pushCustomizeMessage(final PustMessageDTO dto, final List<String> userIdList) {
        final byte[] content = Jsons.toJson(dto).getBytes(Constants.UTF_8);
        PushContext context = new PushContext(content);
        if (userIdList == null) {
            context.setBroadcast(true);
        } else if (userIdList.size() == 1) {
            context.setUserId(userIdList.get(0));
        } else if (userIdList.size() > 1) {
            context.setUserIds(userIdList);
        }
        context.setCallback(new PushCallback() {
            int retryCount = 0;

            public void onSuccess(String userId, ClientLocation location) {
//                DiscoveryPushMessageService.LOGGER.info("[pushCustomizeMessage]message send success!userId={},location={},dto={}", new Object[] { userId,
//                        JSON.toJSONString(location), JSON.toJSONString(this.val$dto) });
//                String toCode = userId;
//                if (userId != null && userId.contains("$")) {
//                    String[] tmpUid = StringUtils.split(userId, "\\$");
//                    toCode = tmpUid[1];
//                }
//                MessageController.cachePush.remove(toCode);
//                if (dto.getMessageType().intValue() == 1) {
//                    DiscoveryPushMessageService.this.userMessageService.checkSysMessageInsertUserMessage(dto.getTo_app_code(), toCode, Integer.valueOf(1));
//                    DiscoveryPushMessageService.this.systemMessageService.updateFactNumBySysMessageId(dto.getMid(), Integer.valueOf(1));
//                } else {
//                    DiscoveryPushMessageService.this.userMessageService.updateReadStatusByIds(Integer.valueOf(1), new String[] { this.val$dto.getMid() });
//                }
            }

            public void onFailure(String userId, ClientLocation location) {
//                DiscoveryPushMessageService.LOGGER.error("[pushCustomizeMessage]message push fail!userId={},location={},dto={}", new Object[] { userId,
//                        JSON.toJSONString(location), JSON.toJSONString(this.val$dto) });
            }

            public void onOffline(String userId, ClientLocation location) {
//                DiscoveryPushMessageService.LOGGER.info("[pushCustomizeMessage] device offline!userId={},location={},dto={}", new Object[] { userId,
//                        JSON.toJSONString(location), JSON.toJSONString(this.val$dto) });
//                if (location != null) {
//                    String os = location.getOsName().toLowerCase();
//                    if (os.contains("ios")) {
//                        String[] partUid = StringUtils.split(userId, "\\$");
//                        if (partUid.length != 3) {
//                            DiscoveryPushMessageService.LOGGER.error("[pushCustomizeMessage]invalid usersId, userId={}, userIdList={}", userId,
//                                    JSON.toJSONString(userIdList));
//                            return;
//                        }
//                        PushUser quyPushUser = new PushUser();
//                        quyPushUser.setAppCode(partUid[0]);
//                        quyPushUser.setUid(partUid[1]);
//                        PushUser tmpPushUser = DiscoveryPushMessageService.this.pushUserService.selectPushUser(quyPushUser);
//                        if (tmpPushUser == null) {
//                            DiscoveryPushMessageService.LOGGER.error("[pushCustomizeMessage]userId={},userIdList={}, , userId,
//                                    JSON.toJSONString(userIdList));
//                            return;
//                        }
//                        DiscoveryPushMessageService.LOGGER.info("[pushCustomizeMessage][1]userId={}, pushUser={}", userId, JSON.toJSONString(tmpPushUser));
//                        if (StringUtils.isBlank(tmpPushUser.getToken())) {
//                            DiscoveryPushMessageService.LOGGER.error("[pushCustomizeMessage]userId={},userIdList={} , userId,
//                                    JSON.toJSONString(userIdList));
//                            return;
//                        }
//                        if (StringUtils.equals(tmpPushUser.getToken(), "android")) {
//                            DiscoveryPushMessageService.LOGGER.error("[pushCustomizeMessage]userId={}, userIdList={},, userId,
//                                    JSON.toJSONString(userIdList));
//                            return;
//                        }
//                        DiscoveryPushMessageService.this.push2APNs(tmpPushUser.getToken(), dto);
//                    } else if (os.contains("android")) {
//                        if (os.contains("xiaomi")) {
//                            DiscoveryPushMessageService.this.send2MiPush(dto, userId);
//                        } else if (os.contains("huawei")) {
//                            DiscoveryPushMessageService.this.send2HuaweiPush(dto, userId);
//                        } else {
//                            DiscoveryPushMessageService.this.send2JPush(dto, userId);
//                        }
//                    } else {
//                        DiscoveryPushMessageService.this.saveOfflineMsg(new OfflineMsg(userId, content));
//                    }
//                } else {
//                    DiscoveryPushMessageService.LOGGER.warn("[pushCustomizeMessage]ClientLocation is null, save offline, userId={},userIdList={}", userId,
//                            JSON.toJSONString(userIdList));
//                    DiscoveryPushMessageService.this.saveOfflineMsg(new OfflineMsg(userId, content));
//                }
            }

            public void onTimeout(String userId, ClientLocation location) {
//                if (this.retryCount++ > 1) {
//                    DiscoveryPushMessageService.this.saveOfflineMsg(new OfflineMsg(userId, content));
//                } else {
//                    DiscoveryPushMessageService.this.send2MPush(dto, userIdList);
//                }
            }
        });
        this.mpusher.send(context);
        return true;
    }

//    private void push2ANPsDirectly(String deviceToken, PustMessageDTO dto) {
//        PushCertificate pushCertificate = this.pushCertificateService.selectPushCertificateByCertCode(dto.getTo_app_code());
//        if (pushCertificate == null) {
//            LOGGER.error("[push2ANPsDirectly]deviceToken={}, , deviceToken);
//            return;
//        }
//        LOGGER.info("[push2ANPsDirectly][2]deviceToken={}, certificatePath={}, pushCertificate={}, ", new Object[] { deviceToken, certificatePath, JSON.toJSONString(pushCertificate) });
//        ApnsService service = null;
//        try {
//            service = APNS.newService().withCert(certificatePath + pushCertificate.getCertPath(), pushCertificate.getCertPass()).withSandboxDestination().withAppleDestination(!this.isSandbox).build();
//        } catch (Exception e) {
//            LOGGER.error("[push2ANPsDirectly] build ApnsService error, certificatePath={},deviceToken={}", new Object[] { certificatePath, deviceToken, e });
//            return;
//        }
//        LOGGER.info("[push2ANPsDirectly][3]deviceToken={}, ApnsService={}", deviceToken, service);
//        PayloadBuilder builder = APNS.newPayload().badge(dto.getBadge().intValue()).sound(dto.getSound()).alertBody(dto.getSub_title_ios());
//        String payload = null;
//        if (StringUtils.isNotBlank(dto.getSub_voice_title_ios())) {
//            JSONObject tmpObject = JSON.parseObject(builder.build());
//            JSONObject apsJSON = tmpObject.getJSONObject("aps");
//            JSONObject jsonObject = (JSONObject)JSON.parseObject(dto.getExtra(), JSONObject.class);
//            JSONObject apsObject = new JSONObject();
//            apsObject.putAll((Map)apsJSON);
//            apsObject.putAll((Map)jsonObject);
//            tmpObject.put("aps", apsObject);
//            payload = tmpObject.toJSONString();
//        } else {
//            payload = builder.build();
//        }
//        try {
//            LOGGER.info("[push2ANPsDirectly][4] start, {}, payload={}", deviceToken, payload);
//            ApnsNotification notification = service.push(deviceToken, payload);
//            LOGGER.info("[push2ANPsDirectly][5] complete! deviceToken={}, ApnsNotification={}", deviceToken,
//                    JSON.toJSONString(notification));
//        } catch (Exception e) {
//            LOGGER.error("DiscoveryPushMessageService#push2ANPsDirectly,push to APNs error", e);
//        }
//    }

    private void send2ANPs(String userId, String deviceToken, PustMessageDTO dto) {
//        LOGGER.info("[send2ANPs][1]request userId={}, deviceToken={}, dto={}", new Object[] { userId, deviceToken, JSON.toJSONString(dto) });
//        String[] partUid = StringUtils.split(userId, "\\$");
//        PushUser quyPushUser = new PushUser();
//        quyPushUser.setAppCode(partUid[0]);
//        quyPushUser.setUid(partUid[1]);
//        PushUser tmpPushUser = this.pushUserService.selectPushUser(quyPushUser);
//        if (tmpPushUser == null) {
//            LOGGER.error("[send2ANPs]userId={}, , userId);
//            return;
//        }
//        LOGGER.info("[send2ANPs][1]userId={}, pushUser={}", userId, JSON.toJSONString(tmpPushUser));
//        if (StringUtils.isBlank(tmpPushUser.getToken())) {
//            LOGGER.error("[send2ANPs]userId={}, , userId);
//            return;
//        }
//        if (StringUtils.equals(tmpPushUser.getToken(), "android")) {
//            LOGGER.error("[send2ANPs]userId={}, , userId);
//            return;
//        }
//        PushCertificate pushCertificate = this.pushCertificateService.selectPushCertificateByCertCode(dto.getTo_app_code());
//        if (pushCertificate == null) {
//            LOGGER.error("[send2ANPs]userId={}, , userId);
//            return;
//        }
//        LOGGER.info("[send2ANPs][2]userId={}, certificatePath={}, pushCertificate={}, ", new Object[] { userId, certificatePath, JSON.toJSONString(pushCertificate) });
//        Integer badge = Integer.valueOf(((tmpPushUser.getBadge() == null) ? 0 : tmpPushUser.getBadge().intValue()) + 1);
//        ApnsService service = null;
//        try {
//            service = APNS.newService().withCert(certificatePath + pushCertificate.getCertPath(), pushCertificate.getCertPass()).withSandboxDestination().withAppleDestination(true).build();
//        } catch (Exception e) {
//            LOGGER.error("[send2ANPs]userId={}, Exception={}", new Object[] { userId, e.getMessage(), e });
//            return;
//        }
//        LOGGER.info("[send2ANPs][3]userId={}, pushCertificate={}", userId, service);
//        PayloadBuilder builder = APNS.newPayload().badge(badge.intValue()).sound(dto.getSound()).alertBody(dto.getSub_title_ios());
//        String payload = null;
//        if (dto.getSub_voice_title_ios() != null) {
//            JSONObject tmpObject = JSON.parseObject(builder.build());
//            JSONObject apsJSON = tmpObject.getJSONObject("aps");
//            apsJSON.put("mutable-content", Integer.valueOf(1));
//            JSONObject alertJSON = new JSONObject();
//            alertJSON.put("voice", dto.getSub_voice_title_ios());
//            alertJSON.put("body", dto.getSub_title_ios());
//            apsJSON.put("alert", alertJSON);
//            tmpObject.put("aps", apsJSON);
//            payload = tmpObject.toJSONString();
//        } else {
//            payload = builder.build();
//        }
//        LOGGER.info("[send2ANPs][4]userId={}, payload={}", userId, payload);
//        service.push(tmpPushUser.getToken(), payload);
//        LOGGER.info("[send2ANPs][5]userId={}, service succeed", userId);
//        this.pushUserService.updatePushUserBadge(badge, tmpPushUser.getPushUserId());
//        LOGGER.info("[send2ANPs][6]userId={}, update badge succeed end", userId);
//        service = null;
//        MessageController.cachePush.remove(partUid[1]);
    }

    private void send2MiPush(PustMessageDTO dto, String userId) {}

    private void send2HuaweiPush(PustMessageDTO dto, String userId) {}

    private void send2JPush(PustMessageDTO dto, String userId) {}

//    private void saveOfflineMsg(OfflineMsg offlineMsg) {}
}
