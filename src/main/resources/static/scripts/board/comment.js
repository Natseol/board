const commentListElem = document.getElementById("commentBox");
console.log("테스트"+`${commentListElem.dataset.boardId}`+"테스트");

const getList = async () => {
    const data = (await axios.get(`/comments?boardId=${commentListElem.dataset.boardId}
    &start=${commentListElem.children.length}`)).data;
    console.log(commentListElem);
    console.log(data);

    if (data.end) {
        document.getElementById("add-comment-btn").outerHTML = "";
    }

    const commentBox = document.getElementById("commentBox");
    // commentBox.innerHTML = "";
    if (data.list.length == 0) {
        commentBox.innerHTML = "작성된 댓글이 없습니다";
    }

    data.list.forEach(comment => {
        const commentElement = createCommentElement(comment, 0);
        commentBox.append(commentElement);
    });
};

function createCommentElement(comment, depth) {
    const commentItem = document.createElement("div");
    commentItem.className = "comment-item";
    commentItem.style.marginLeft = `${depth * 20}px`; // Adjust the indentation (you can change the value as needed)

    const commentUserId = document.createElement("div");
    commentUserId.className = "comment-userId";
    commentUserId.innerHTML = `${comment.userStrId}`;

    const commentContent = document.createElement("div");
    commentContent.className = "comment-content";
    commentContent.innerHTML = `${comment.content}`;

    const commentCreated = document.createElement("div");
    commentCreated.className = "comment-created";
    commentCreated.innerHTML = `${new Date(comment.createdAt).toDateString()}`;

    const commentButtonOpenWrite = document.createElement("button");
    commentButtonOpenWrite.className = "comment-button-open-write";
    commentButtonOpenWrite.innerText = "답글쓰기";
    if (sessionId != "") {
        commentButtonOpenWrite.addEventListener("click", () => toggleReplyForm(commentItem));
    }

    commentItem.append(commentUserId);
    commentItem.append(commentContent);
    commentCreated.append(commentButtonOpenWrite);
    commentItem.append(commentCreated);
    const commentHr = document.createElement("hr");
    commentItem.append(commentHr);

    const replyForm = createReplyForm(comment);
    commentItem.append(replyForm);

    //재귀함수
    if (comment.children && comment.children.length > 0) {
        const nestedCommentBox = document.createElement("div");
        nestedCommentBox.className = "nested-comment-box";

        comment.children.forEach(nestedComment => {
            const nestedCommentElement = createCommentElement(nestedComment, depth + 1);
            nestedCommentBox.append(nestedCommentElement);
        });

        commentItem.append(nestedCommentBox);
    }

    return commentItem;
}

function createReplyForm(parentComment) {
    const form = document.createElement("form");
    form.className = "comment-form"
    form.action = "/comments/add";
    form.method = "post";

    const commentWriter = document.createElement("div");
    commentWriter.className = "commenet-writer";
    commentWriter.innerHTML = sessionUserId;

    const formFloating = document.createElement("div");
    formFloating.className = "form-floating mt-1 mb-1";

    const textarea = document.createElement("textarea");
    textarea.className = "form-control";
    textarea.placeholder = "content";
    textarea.id = `reply-intput-content-${parentComment.id}`;
    textarea.name = "content";

    const hiddenUserId = document.createElement("input");
    hiddenUserId.type = "hidden";
    hiddenUserId.name = "user_id";
    hiddenUserId.value = sessionId;

    const hiddenBoardId = document.createElement("input");
    hiddenBoardId.type = "hidden";
    hiddenBoardId.name = "board_id";
    hiddenBoardId.value = boardId;

    const hiddenParentId = document.createElement("input");
    hiddenParentId.type = "hidden";
    hiddenParentId.name = "parent_id";
    hiddenParentId.value = parentComment.id;

    const label = document.createElement("label");
    label.htmlFor = `reply-content-${parentComment.id}`;
    label.className = "comment-label";
    label.innerText = "댓글을 남겨보세요";

    const commentButtonBox = document.createElement("div");
    commentButtonBox.className = "comment-button-box";

    const submitButton = document.createElement("button");
    submitButton.className = "comment-button-write me-2";
    submitButton.innerText = "등록";

    formFloating.append(textarea);
    formFloating.append(hiddenUserId);
    formFloating.append(hiddenBoardId);
    formFloating.append(hiddenParentId);
    formFloating.append(label);

    commentButtonBox.append(submitButton);

    form.append(commentWriter);
    form.append(formFloating);
    form.append(commentButtonBox);

    const commentHr = document.createElement("hr");
    form.append(commentHr);

    form.style.display = "none";

    return form;
}

function toggleReplyForm(commentItem) {
    const replyForm = commentItem.querySelector("form");
    // const replyFormsAll = document.querySelectorAll("form");

    // replyFormsAll.forEach(form => {
    //     form.style.display = "none";
    // });

    if (replyForm.style.display === "none") {
        replyForm.style.display = "block";
    } else {
        replyForm.style.display = "none";
    }
}

getList();

document.getElementById("add-comment-btn").onclick = () => {
    getList();
}