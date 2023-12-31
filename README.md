# Vodafone Learn Documentation

![Vodafone Learn Logo](https://github.com/karimhesham1/VodaLearningPlatform/blob/master/img/Vodafone-Learn-Logo.png)

---

## Table of Contents

1. [Introduction](#introduction)
    - [Overview](#overview)
    - [Technologies Used](#tech)
2. [API Documentation](#api-doc)
    - [Endpoints](#endpoints)
      - [Post /post](#upload)
      - [PUT /post/{postId}](#update)
      - [GET /post](#read)
      - [DELETE /post/{postId}](#delete)
3. [High Level Scenarios](#hls)
    - [HLS 1: User Uploads a Post](#hls-upload)
    - [HLS 2: User Update a Post](#hls-update)
    - [HLS 3: User Read a Post](#hls-read)
    - [HLS 4: User Delete a Post](#hls-delete)
4. [Testing](#testing)
    - [Setting Up Testing Environment](#setting-up-testing)
    - [Unit Testing with JUnit 5](#junit)
5. [Appendix](#appendix)
    - [Sample Requests and Responses](#sample-req-respon)
    - [Code Examples](#code-examples)  

---

## 1. Introduction
### Overview
Welcome to the Vodafone Learn Backend Documentation. This guide provides an in-depth understanding of the backend components that power the Vodafone Learn learning hub application. Here, you will find details about the APIs, endpoints, high-level scenarios, testing procedures, and troubleshooting guidance specific to the backend architecture.

### Technologies Used
- OpenAPI for clear and comprehensive API documentation
- Spring Boot for rapid application development and management
- H2 Database for efficient testing and development
- JUnit 5 for robust unit and integration testing

---

## 2. API Documentation
### Endpoints

#### 'POST /post'
Description: Uploads a new post to the platform.

#### 'PUT /post/{postId}'
Description: Updates an existing post using the postId.

---

## 3. High Level Scenarios

### HLS 1: User Uploads a Post

**Scenario:** A user uploads a new post to the platform.

**Steps:**
1. User navigates to the "Upload" section.
2. User provides post details such as title, description, and tags.
3. User submits the post.
4. Backend receives the post data.
5. Backend validates the input data, including mandatory fields.
6. Backend creates a new post record in the database.
7. Backend generates a unique post ID.
8. Backend responds to the user with a success message and the post ID.

**Outcome:** The user's post is successfully uploaded to the platform.

### HLS 2: User Updates a Post

**Scenario:** A user updates an existing post.

**Steps:**
1. User navigates to the "Edit" section.
2. User selects the post they want to update.
3. User modifies the post content, including title, description, and tags.
4. User submits the changes.
5. Backend receives the update request along with the modified data.
6. Backend fetches the post from the database using the post ID.
7. Backend updates the post data with the new content.
8. Backend confirms the update and sends a success response to the user.

**Outcome:** The user's post is successfully updated with the new content.

### HLS 3: User Reads a Post
**Scenario:** A user reads the details of a specific post.

**Steps:**
1. User selects a post to read.
2. User navigates to the "Read" section.
3. User requests the details of the selected post.
4. Backend receives the read request along with the postId.
5. Backend fetches the post details from the database using the postId.
6. Backend responds with the post details.

**Outcome:** The user successfully reads the details of the selected post.

### HLS 4: User Deletes a Post
**Scenario:** A user deletes an existing post.

**Steps:**
1. User selects a post to delete.
2. User confirms the deletion.
3. Backend receives the delete request along with the postId.
4. Backend fetches the post from the database using the postId.
5. Backend marks the post as deleted.
6. Backend responds to the user with a success message.

**Outcome:** The user's selected post is successfully deleted from the platform.

---

## 5. Testing

### Unit Tests: PostService

#### `testCreatePost_SuccessfulPostCreation`

**Scenario:** Successfully create a new post.

**Given:** A post request with title and description.

**When:** The `createPost` method is called with the post request.

**Then:**
- The post response title matches the input title.
- The post response description matches the input description.
- The post ID is not null.
- Calling `createPost` again does not throw any exceptions.

---

#### `testCreatePost_SuccessfulPostCreationMultipleTags`

**Scenario:** Successfully create a new post with multiple tags.

**Given:** A post request with title, description, and multiple tags.

**When:** The `createPost` method is called with the post request.

**Then:**
- The post response title matches the input title.
- The post response description matches the input description.
- The post ID is not null.
- Calling `createPost` again does not throw any exceptions.

---

#### `testCreatePost_SuccessfulPostCreationMissingDescription`

**Scenario:** Successfully create a new post with a missing description.

**Given:** A post request with title but no description.

**When:** The `createPost` method is called with the post request.

**Then:**
- The post response title matches the input title.
- The post response description matches the input description.
- The post ID is not null.
- Calling `createPost` again does not throw any exceptions.

---

#### `testCreatePost_FailureMissingTitle`

**Scenario:** Fails to create a post due to missing title.

**Given:** A post request with description but no title.

**When:** The `createPost` method is called with the post request.

**Then:** An `IllegalArgumentException` is thrown with the expected error message.

---

#### `testCreatePost_FailureMissingTags`

**Scenario:** Fails to create a post due to missing tags.

**Given:** A post request with title, description, but no tags.

**When:** The `createPost` method is called with the post request.

**Then:** An `IllegalArgumentException` is thrown with the expected error message.

---

#### `testCreatePost_FailureNullTag`

**Scenario:** Fails to create a post due to null tag.

**Given:** A post request with title, description, and a null tag.

**When:** The `createPost` method is called with the post request.

**Then:** An `IllegalArgumentException` is thrown with the expected error message.

---

#### `testCreatePost_FailureNullTitle`

**Scenario:** Fails to create a post due to null title.

**Given:** A post request with description and a null title.

**When:** The `createPost` method is called with the post request.

**Then:** An `IllegalArgumentException` is thrown with the expected error message.

---

#### `testCreatePost_FailureServiceUnavailable`

**Scenario:** Fails to create a post due to service being unavailable.

**Given:** A post request with title and description.

**When:** The `createPost` method is called with the post request.

**Then:** A `ServiceUnavailableException` is thrown.

### Unit Tests: PostDeleteService

#### `testDeletePost_SuccessfulPostDeletion`

**Scenario:** Successfully delete an existing post.

**Given:** An existing post.

**When:** The `deletePost` method is called with the post ID.

**Then:**
- The post is marked as deleted in the database.
- Calling `deletePost` again for the same post ID throws a `NotFoundException`.

#### `testDeletePost_NonexistentPost`

**Scenario:** Attempt to delete a nonexistent post.

**Given:** A nonexistent post ID.

**When:** The `deletePost` method is called with the nonexistent post ID.

**Then:** A `NotFoundException` is thrown.

#### `testDeletePost_AlreadyDeletedPost`

**Scenario:** Attempt to delete an already deleted post.

**Given:** An already deleted post.

**When:** The `deletePost` method is called with the deleted post ID.

**Then:** A `NotFoundException` is thrown.

---

## 6. Appendix

### Sample Requests and Responses

#### Upload Post (POST /post)

**Request:**
```json
{
  "title": "Spring boot",
  "description": "important in backend team",
  "tag": [
    {
      "tagName": "Backend"
    }
  ],
  "attachment": [
    {
      "attachment": "https://example.com/attachment.pdf"
    }
  ]
}
```
**Responses:**
* Status 201 Created
```json
{
  "postId": "unique_post_id",
  "title": "Springboot",
  "description": "important in backend team",
  "tag": [
    {
      "tagName": "Backend"
    }
  ],
  "attachment": [
    {
      "attachment": "https://example.com/attachment.pdf"
    }
  ]
}
```

* Status 400 Bad Request
```json
{
  "message": "Invalid"
}
```

### Update Post (PUT /post/{postId})

**Request:**
```json
{
  "title": "Updated Spring boot",
  "description": "updated description",
  "tag": [
    {
      "tagName": "Backend"
    },
    {
      "tagName": "Update"
    }
  ],
  "attachment": [
    {
      "attachment": "https://example.com/updated_attachment.pdf"
    }
  ]
}
```

**Responses:**
* Status 202 Accepted
```json
{
  "postId": "unique_post_id",
  "title": "Updated Spring boot",
  "description": "updated description",
  "tag": [
    {
      "tagName": "Backend"
    },
    {
      "tagName": "Update"
    }
  ],
  "attachment": [
    {
      "attachment": "https://example.com/updated_attachment.pdf"
    }
  ]
}
```

* Status 404 Not Found
```json
{
  "message": "Not Found"
}
```
### Delete Post (DELETE /post/{postId})

**Responses**

* Status 202 Accepted
```json
{
  "message": "Deleted Successfully"
}
```

* Status 404 Not Found
```json
{
  "message": "Not Found"
}
```

### Get Post (GET /post Get items using a Map object as a query parameter)

**Request**

```json
{
  "additionalProp1": "string",
  "additionalProp2": "string",
  "additionalProp3": "string"
}
```

**Responses**

* Status 200 Successful Response
```json
{
  "posts": [
    {
      "postId": 0,
      "title": "Springboot",
      "description": "important in backend team",
      "tag": [
        {
          "tagName": "string"
        }
      ],
      "attachment": [
        {
          "attachment": "string"
        }
      ]
    }
  ]
}
```

* Status 404 Not Found

```json
{
  "message": "Post Not Found"
}
```
