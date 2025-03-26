# 📝 Blog Application API  

Welcome to the **Blog Application API**! This project provides a simple RESTful API for managing blog posts, including CRUD operations, pagination, and blog summarization using OpenAI.  

---

## 📦 Tech Stack  

- **Spring Boot** ☕  
- **MySQL** 🛢️  
- **Docker** 🐳  
- **OpenAI API** 🤖  

---

## 🚀 Setup Instructions  

### 🛢️ Clone the Repository  

```sh
git clone https://github.com/Rohan-Ghadage/Blog-API.git
cd Blog-API
```

### 🛢️ Build the Project  

Make sure you have **Maven** installed. Run:  

```sh
mvn clean package
```

### 🛢️ Run with Docker  

1️⃣ **Build and run containers** 🐳  

```sh
docker-compose up --build -d
```

2️⃣ Check running containers:  

```sh
docker ps
```

3️⃣ Access the API at:  

```
http://localhost:5457/api/blogs
```

---

## 🌟 API Endpoints  


### 🟡 **Create a New Blog**  

```http
POST /api/blogs
```

📌 **Request Body (JSON):**  

```json
{
  "title": "My First Blog",
  "content": "This is an amazing blog post!",
  "author": "Rohan"
}
```
📌 **Response:** Returns the created blog post.  

---

### 🟢 **Get Blog by ID**  

```http
GET /api/blogs/{id}
```
📌 **Response:** Returns a single blog post by ID.  

---
### 🟢 **Get All Blogs (Paginated)**  

```http
GET /api/blogs/paginated?page=0&size=5
```

📌 **Response:** Returns paginated list of blogs.  

---

### 🟢 **Get Blog Summary**  

```http
GET /api/blogs/summary/{id}
```

📌 **Response:** Returns AI-generated summary for a blog post.  

---

### 🟠 **Update a Blog**  

```http
PUT /api/blogs/{id}
```

📌 **Request Body (JSON):** 
```json
{
  "title": "My Updated Blog",
  "content": "This is an amazing updated blog post!",
  "author": "Rohan Ghadage"
}
```

📌 **Response:** 
```json
{
  "id": "2"
  "title": "My Updated Blog",
  "content": "This is an amazing updated blog post!",
  "author": "Rohan Ghadage"
}
```
---

### 🔴 **Delete a Blog**  

```http
DELETE /api/blogs/{id}
```

📌 **Response:** Blog deleted with id:" ".  

---

## 🛠️ Docker Configuration  

### **Dockerfile**  

```dockerfile
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/blog-app-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 5457
CMD ["java", "-jar", "app.jar"]
```

### **docker-compose.yml**  

```yaml
version: '3.8'

services:
  mysql:
    image: mysql:8.0
    container_name: mysql-db
    restart: always
    environment:
      MYSQL_DATABASE: blogapi
      MYSQL_ROOT_PASSWORD: Mysql@143
    ports:
      - "3307:3306"
    volumes:
      - mysql_data:/var/lib/mysql
    networks:
      - blog-network

  app:
    build: .
    container_name: blog-app
    restart: always
    depends_on:
      - mysql
    environment:
      SPRING_DATASOURCE_URL: jdbc:mysql://mysql:3306/blogapi
      SPRING_DATASOURCE_USERNAME: root
      SPRING_DATASOURCE_PASSWORD: Mysql@143
      SPRING_JPA_HIBERNATE_DDL_AUTO: update
    ports:
      - "5457:5457"
    networks:
      - blog-network

volumes:
  mysql_data:

networks:
  blog-network:

```
![image](https://github.com/user-attachments/assets/e9d9cb07-554f-4d33-ab11-3a9d20c4cc2f)
![image](https://github.com/user-attachments/assets/ba3d90c8-c9e4-4824-8cf0-89738bde5619)

---

## 📩 Contact  

💡 Have suggestions or found a bug? Create an issue or contribute! 🛠️  

📌 **Author:** Rohan Ghadage
📌 **Email:** ghadagerohan07@gmail.com
📌 **GitHub:** https://github.com/Rohan-Ghadage/Blog-API.git

---

