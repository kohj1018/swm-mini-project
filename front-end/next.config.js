/** @type {import('next').NextConfig} */
const nextConfig = {
  reactStrictMode: true,
  images: {
    domains: [
      "static.toiimg.com",
      "localhost:8080",
      "images.unsplash.com"
    ],
  },
};

module.exports = nextConfig
