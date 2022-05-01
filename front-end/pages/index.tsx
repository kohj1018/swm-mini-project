import { NextPage } from "next";
import Post from "../components/Post";
import {useEffect, useState} from "react";
import {postApi} from "../api";

const MainPage: NextPage = () => {
  const [posts, setPosts] = useState([]);

  useEffect(() => {
    const getPosts = async () => {
      const data = await postApi.latestOrder();
      setPosts(data.data);
    }

    getPosts();
  })

  return (
    <main className="pt-10 pb-36 bg-neutral-100">
      <div className="flex flex-col items-center gap-12">
        {/* 임시 => posts로 바꿔주면 됨. */}
        {posts.map((postInfo) => {
          return (
            <Post
              key={postInfo.postId}
              postId={postInfo.postId}
              writerId={postInfo.writerId}
              restaurantName={postInfo.restaurantName}
              image={postInfo.image}
              likeCnt={postInfo.likeCnt}
              text={postInfo.text}
            />
          )
        })}
      </div>
    </main>
  )
}

export default MainPage