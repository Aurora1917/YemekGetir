# 🍔 Yemek Getir: Dağıtık Yemek Sipariş Platformu

Bu proje, modern bir yemek sipariş uygulamasının arka uç (backend) sistemini temel alan, microservice mimarisiyle geliştirilmiş tamamen dağıtık bir sistemdir.  
Amaç; her iş alanını (Kullanıcı, Sipariş, Restoran, Menü) bağımsız servisler halinde yöneterek ölçeklenebilir, esnek ve sürdürülebilir bir yapı kurmaktır.

---

## 🚀 Proje Mimarisi ve Servisler

Sistem, Spring Cloud ekosistemi ve Docker Compose kullanılarak ayağa kaldırılmıştır.

### Temel Servisler
- **Discovery Service (Port: 8761)**: Tüm servislerin kendini kaydettiği ve birbirini bulduğu Eureka sunucusudur.  
- **Config Server (Port: 8888)**: Tüm servislerin konfigürasyonlarını merkezi bir Git deposundan çeken yapılandırma sunucusudur.  
- **API Gateway (Port: 8080)**: Tüm dış istekleri karşılayan tek giriş noktasıdır. İstekleri ilgili servislere yönlendirir.  

### İş Servisleri
- **User Service (Port: 8081)**: Kullanıcı kaydı ve yönetimi (Veritabanı: PostgreSQL - `user_db`)  
- **Restaurant Service (Port: 8082)**: Restoran kayıt ve bilgileri (Veritabanı: PostgreSQL - `restaurant_db`)  
- **Order Service (Port: 8083)**: Siparişlerin oluşturulması ve yönetimi (Veritabanı: PostgreSQL - `order_db`)  
- **Menu Service (Port: 8084)**: Restoranlara ait menü ve ürünlerin yönetimi (Veritabanı: PostgreSQL - `menu_db`)  

---

## 🔄 Servisler Arası İletişim

Servisler, `@LoadBalanced RestTemplate` kullanarak haberleşir.  
Bu sayede servisler birbirine IP adresi yerine doğrudan servis adıyla (örneğin `http://MENUSERVICE`) istek gönderebilir.

---

## 🧠 Kullanılan Teknolojiler

- **Dil & Framework:** Java 21, Spring Boot 3  
- **Microservice Çatısı:** Spring Cloud (Eureka, Config, Gateway)  
- **Veritabanı:** PostgreSQL (Her servis için ayrı instance)  
- **Konteynerleştirme:** Docker & Docker Compose  
- **API Dokümantasyonu:** Springdoc & Swagger  

---

## ⚙️ Kurulum ve Çalıştırma

### Önkoşullar
Sistemde aşağıdaki yazılımların kurulu olması gerekir:
- Git  
- Maven  
- Docker  
- Docker Compose  

### Adımlar

1. **Projeyi Klonla**
   ```bash
   git clone https://github.com/Aurora1917/YemekGetir
   cd YemekGetir
