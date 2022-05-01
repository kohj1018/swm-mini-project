import axios from "axios";

const api = axios.create({
  baseURL: "http://localhost:8080/",
})

export const postApi = {
  latestOrder: () => api.get("/api/post")
}