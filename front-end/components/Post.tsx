import { NextPage } from "next";
import Image from "next/image";
import defaultUserImg from "../assets/icon_user.svg";
import heart from "../assets/icon_heart.svg";
import redHeart from "../assets/icon_redheart.svg";
import comment from "../assets/icon_comment.svg";
import {useState} from "react";

interface postProps {
  postId: string;
  writerId: string;
  restaurantName: string;
  image: string;
  likeCnt: number;
  text: string;
}

const Post: NextPage<postProps> = ({ postId, writerId, restaurantName, image, likeCnt, text }) => {
  const [likeCount, setLikeCount] = useState(likeCnt);
  const [isLikeChecked, setIsLikeChecked] = useState(false);

  const clickHeart = () => {
    if (isLikeChecked) {
      setLikeCount(likeCnt - 1);
      setIsLikeChecked(false);
    } else {
      setLikeCount(likeCnt + 1);
      setIsLikeChecked(true);
    }
  }


  return (
    <article className="w-[90vw] max-w-[650px] h-[117vw] max-h-[850px] bg-white border border-[#dbdbdb]">
      <div className="flex items-center px-5 w-full h-[7.65%] max-h-[65px] border-b border-[#dbdbdb]">
        <div className="relative !w-[5.5vw] !max-w-[40px] !h-[5.5vw] !max-h-[40px]">
          <Image
            className="!border !border-solid !border-[#dbdbdb] rounded-full"
            src={defaultUserImg}
            layout="fill"
            objectFit="contain"
            alt="유저 프로필"
          />
        </div>
        <div className="flex flex-col justify-center ml-6">
          <p style={{fontSize: 'min(3.3vw, 24px)', lineHeight: 'min(3.3vw, 24px)'}} className="font-bold">{writerId}</p>
          <p style={{fontSize: 'min(2.2vw, 16px)', lineHeight: 'min(2.2vw, 16px)'}} className="mt-1">{restaurantName}</p>
        </div>
      </div>
      <div className="relative w-full h-[67.64%] max-h-[575px]">
        <Image
          src={image}
          layout="fill"
          objectFit="contain"
          alt="음식 사진"
        />
      </div>
      <div className="w-full h-[18.82%] max-h-[160px] border-y border-[#dbdbdb] px-4 pt-[2%]">
        <div className="flex justify-between items-center mb-[4%]">
          <div className="flex justify-between items-center">
            <div className="relative !w-[4.1vw] !max-w-[30px] !h-[4.1vw] !max-h-[30px] cursor-pointer" onClick={clickHeart}>
              <Image
                className="hover:scale-110"
                src={isLikeChecked ? redHeart : heart}
                layout="fill"
                objectFit="contain"
                alt="좋아요"
              />
            </div>
            <div className="relative !w-[4.1vw] !max-w-[30px] !h-[4.1vw] !max-h-[30px] ml-3 cursor-pointer">
              <Image
                className="hover:scale-110"
                src={comment}
                layout="fill"
                objectFit="contain"
                alt="댓글"
              />
            </div>
          </div>
          <p style={{fontSize: 'min(2.2vw, 16px)', lineHeight: 'min(2.2vw, 16px)'}} className="font-bold">좋아요 {likeCount.toString()}개</p>
        </div>
        <div className="flex">
          <p style={{fontSize: 'min(2.2vw, 16px)', lineHeight: 'min(2.2vw, 16px)'}} className="font-bold">{writerId}</p>
          <p style={{fontSize: 'min(2.2vw, 16px)', lineHeight: 'min(2.2vw, 16px)'}} className="ml-4">{text}</p>
        </div>
      </div>
      <div className="flex w-full h-[5.89%] max-h-[50px] justify-between items-center px-4">
        <p style={{fontSize: 'min(3.3vw, 24px)', lineHeight: 'min(3.3vw, 24px)'}}>↪</p>
        <input type="text" className="w-[80%]" placeholder="댓글을 입력해주세요."/>
        <div style={{fontSize: 'min(2.2vw, 16px)', lineHeight: 'min(2.2vw, 16px)'}} className="font-bold text-[#0095F6] cursor-pointer hover:scale-105">게시</div>
      </div>
    </article>
  );
};

export default Post;