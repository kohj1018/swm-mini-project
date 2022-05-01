import { NextPage } from "next";
import { clearPreviewData } from "next/dist/server/api-utils";
import { useRouter } from 'next/router';
import { useState, useEffect, useRef, SetStateAction } from "react";
import { render } from "react-dom";
import axios from 'axios';

import write from '../styles/write.module.css';
import { request } from "https";

const Write: NextPage = () => {

  const [MsgState, setMsgState] = useState(''); // user message
  const [foodState, setFoodState] = useState(''); // food
  const [files, setFiles] = useState('');

  const preview = () => {
    if (!files) return false;

    const imgEL = document.getElementsByClassName('img__box') as HTMLCollectionOf<HTMLElement>;
    const reader = new FileReader();

    reader.onload = () => {
      (imgEL[0].style.backgroundImage = `url(${reader.result})`)
    }
    const blobFile = new Blob([files[0]]);
    reader.readAsDataURL(blobFile);
  }

  preview();

  const onImgChange = (e: { target: { files: any[]; }; }) => {
    const img = e.target.files[0];
    const formData = new FormData();
    formData.append('file', img);
  }

  const onTextChange = (e: { target: { value: SetStateAction<string>; }; }) => {
    setMsgState(e.target.value)
  }

  const onFoodChnage = (e: { target: { value: SetStateAction<string>; }; }) => {
    if (e !== null)
      setFoodState(e.target.value)
  }

  const onLoadFile = (e: { target: { files: any; }; }) => {
    const file = e.target.files;
    console.log(file);
    setFiles(file);
  }

  const onSubmit = async (e: { preventDefault: () => void; }) => {
    e.preventDefault();
    const imgEL = document.getElementsByClassName('img__box') as HTMLCollectionOf<HTMLElement>;
    // 컴포넌트 읽기

    const formdata = new FormData(); // 파일 제출
    formdata.append('uploadImage', files[0]);

    const config = {
      Headers: {
        'content-type': 'multipart/form-data',
      },
    }


    // 8주차 실습 구현
    //console.log(e);
    if (MsgState === '') {
      alert("메시지를 입력하세요");
      return;
    }
    if (foodState === '') {
      alert("음식점을 입력하세요!")
      return;
    }
    if (files === '') {
      alert("파일을 추가하세요!")
      return;
    }

    //axios.post('http://localhost:8001/post', {
    //   username: MsgState.username,
    //   message: MsgState.message,
    //   searched: MsgState.searched
    // });
    // axios.post('dfads', formdata, config);
    // 이거랑 비슷하게 메시지 보내줄 모델 찾기
    //{

    // {
    //    “userId” : -,
    //    “restaurantName” : -,
    //    “foodImage” : -,
    //    “description” : -,
    // }

    //

    await axios.get(''); // 여기에서 유저 정보를 받는다.
    await axios.post('http://localhost:8080/api/post', {
      userId: "1",
      restaurantName: foodState,
      foodImage: imgEL[0].style.backgroundImage,
      description: MsgState,
    }, {
      headers: {
        Authorization: "Bearer " + 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ3bGdoa3MwMzE0QGdtYWlsLmNvbSIsImF1dGgiOiJST0xFX0dVRVNUIiwiZXhwIjoxNjUyMDA1MzE2fQ.c8r0MF8EENmlymDuDKjusAN7efOCfB8rxlMEDYXsJS-NapeZyTngQgZDgv8d3T5tD3Z6gUwLKXzmo7BMUXiuvA'
      }
    })

    imgEL[0].style.backgroundImage = ''
    console.log(imgEL[0].style.backgroundImage + 'file exists');
    setMsgState('');
    setFoodState('');
    setFiles('');

    // location.reload();
    //navigate('/', {state:// Your data})
  }

  return (
    <div className={write.frame_4}>
      <form onSubmit={onSubmit} method="post">
        <input type='file'
               accept='image/jpg,impge/png,image/jpeg,image/gif'
               id='files'
               onChange={onLoadFile}
               style={{ display: 'none', outline: 'none' }}
        >
        </input>
        <div className={write.pic_comment}>
          <label htmlFor="files" className={write.rectangle_2 + " img__box"} style={{ display: "block" }}></label>
          <textarea
            placeholder="문구 입력..."
            className={write.rectangle_3}
            onChange={onTextChange}
            value={MsgState}
            style={{ border: 'none', width: "500px", height: "50px", outline: 'none', resize: 'none', padding: '10px' }}>
          </textarea>
          <button id="submit_btn" className={write.submit} >
            <img src='blue-check-mark.svg' style={{ height: '30px', width: '30px', background: 'none' }} />
          </button>
        </div>
        <div className={write.seperator_box}>
          <input
            placeholder="식당 입력..."
            onChange={onFoodChnage}
            value={foodState}
            style={{ border: 'none', outline: 'none' }}
          />
        </div>
      </form >
    </div >
  )
}
// 이모지 사진으로 바꿔서 가져오기?
export default Write

/*
const Frame4: React.VFC = () => {
  return (
    <div className="frame-4">
      <div className="rectangle-1" />
      <div className="cloud-uploading-icon">
        <img src="" />
      </div>
      <div className="rectangle-2" />
      <div className="cloud-uploading-icon">
        <img src="" />
      </div>
      <div className="frame-3">
        <p className="text-3">submit</p>
      </div>
      <div className="rectangle-3" />
      <p className="text-4">📝글 작성</p>
    </div>
  )
}

.frame-4 {
  height: 911px;
  width: 621px;
  background-color: #ffffff;
}
.rectangle-1 {
  height: 911px;
  width: 621px;
  background-color: #ffffff;
}
.cloud-uploading-icon {
  height: 56px;
  width: 56px;
  background-color: #ffffff;
}
.img-1 {
  height: 30px;
  width: 37px;
  background-color: #ffffff;
}
.rectangle-2 {
  height: 417px;
  width: 621px;
  background-color: #c4c4c4;
}
.cloud-uploading-icon {
  height: 56px;
  width: 56px;
  background-color: #ffffff;
}
.img-2 {
  height: 30px;
  width: 37px;
  background-color: #ffffff;
}
.frame-3 {
  border-radius: 5px;
  display: flex;
  flex-direction: row;
  justify-content: flex-start;
  align-items: flex-start;
  padding: 10px 10px 10px 30px;
  gap: 10px;
  background-color: #ffffff;
  border: 1px solid #000000;
}
.text-3 {
  text-align: center;
  vertical-align: middle;
  font-size: 18px;
  font-family: Red Rose;
  line-height: auto;
  color: #000000;
}
.rectangle-3 {
  height: 224px;
  width: 621px;
  background-color: #ffffff;
  border: 1px solid rgba(128, 128, 128, 0.30000001192092896);
}
.text-4 {
  text-align: center;
  vertical-align: middle;
  font-size: 18px;
  font-family: Red Rose;
  line-height: auto;
  color: #000000;
}






import React, {useState} from "react";

import Upload from "../shared/Upload"

import {history} from "../redux/configureStore"

import PublishIcon from '@material-ui/icons/Publish';
import TextField from '@material-ui/core/TextField';
import styled from "styled-components";

import { useDispatch, useSelector } from "react-redux";
import { actionCreators as imageActions } from "../redux/modules/image"
import { actionCreators as postActions} from "../redux/modules/post"


const PostWrite = (props) => {
  const dispatch = useDispatch()
  const is_login = useSelector((state) => state.user.is_login);
  const preview = useSelector((state) => state.image.preview)
  const user_info = useSelector((state) => state.user.user)
  
  const post_list = useSelector((state) => state.post.list);
  const post_id = props.match.params.id;
  const is_edit = post_id ? true : false;
  const _post = is_edit? post_list.find((p) => p.id == post_id) : null;
  const [contents, setContents] = React.useState(_post ? _post.content : "")
  const ok_submit = contents ? true : false
  console.log(_post)
  React.useEffect(() => {
    if (is_edit && !_post) {
      console.log("포스트 정보가 없어요!");
      history.goBack();

      return;
    }

    if (is_edit){
      dispatch(imageActions.setPreview(_post.post_image_url))
    } else{
      dispatch(imageActions.setPreview("http://via.placeholder.com/400x300"))
    }
  }, [])


  const ImageError = () => {
    window.alert('잘못된 이미지 주소입니다.😐')

    dispatch(imageActions.setPreview("http://via.placeholder.com/400x300"))
  }

  const changeContents = (e) => {
    setContents(e.target.value)
  }

  const addPost = () => {
    if(!contents){
      window.alert("😗빈칸을 채워주세요...ㅎㅎ")
      return
    }
    let post ={
      contents: contents,
    }
    console.log(post)
    dispatch(postActions.addPostAX(post))
  }

  const editPost = () => {
    if(!contents){
      window.alert("😗빈칸을 채워주세요...ㅎㅎ")
      return;
    }

    let post={
      contents: contents,
    }
    console.log(post_id)
    dispatch(postActions.editPostAX(post_id , post)) 
  }


  return (
    <React.Fragment>
      <WriteMainContainer>
        <WriteInner>
          <WriteBox>
            <WriteHeader>
              <WriteHeaderLeft>
                <WriteProfile src={user_info.profile_url} />
                <PostAuthor>{user_info.user_name}</PostAuthor>
              </WriteHeaderLeft>
            </WriteHeader>
            <WriteContent>
              <WriteUpload>
              <Upload/>
              </WriteUpload>
              <WriteImg src={preview ? preview : "http://via.placeholder.com/400x300"}
                onError={ImageError}
              />
              <TextField
                id="outlined-multiline-static"
                label="📝글 작성"
                multiline
                rows={6}
                variant="outlined"
                value={contents}
                onChange = {changeContents}
              />
              {is_edit ? (
                <WriteSubmit onClick={editPost}>게시글 수정</WriteSubmit>
              ) : (
                <WriteSubmit onClick={addPost}>게시글 작성</WriteSubmit> 
              )}

              { {ok_submit ? (
                <WriteSubmit onClick={editPost}>게시글 수정</WriteSubmit>
              ): (
                <WriteSubmit style={{opacity: "0.3"}} >게시글 수정</WriteSubmit>
              )} }
              
              </WriteContent >
              </WriteBox >
            </WriteInner >
          </WriteMainContainer >
{/* {is_editcancelmodal ? <ModalForPostEdit close={closeEditCancelModal}/>        
            : null} }
        </React.Fragment >
      )
    
    }
*/