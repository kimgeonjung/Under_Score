$(document).ready(function () {
    // 로그인 모달 열기
    $("#openLoginModal").click(function () {
        $("#modal").show(); // 로그인 모달 보이기
    });

    // 로그인 모달 닫기
    $("#closeLoginModal").click(function () {
        $("#modal").hide(); // 로그인 모달 숨기기
        $("#modal .error").text(''); // 에러 메시지 초기화
    });

    // ID 찾기 모달 열기
    $("#findLoginIdModal").click(function (event) {
        event.preventDefault(); // 기본 동작 방지
        $("#modal").hide(); // 기존 로그인 모달 숨기기
        $("#modal2").show(); // ID 찾기 모달 보이기
    });

    // ID 찾기 모달 닫기
    $("#closeLoginModal2").click(function () {
        $("#modal2").hide(); // ID 찾기 모달 숨기기
        $("#modal").show(); // 로그인 모달 다시 보이기
    });

    // 비밀번호 변경 모달 열기
    $("#changePwModal").click(function (event) {
        event.preventDefault(); // 기본 동작 방지
        $("#modal").hide(); // 기존 로그인 모달 숨기기
        $("#modal3").show(); // 비밀번호 변경 모달 보이기
    });

    // 비밀번호 변경 모달 닫기
    $("#closeLoginModal3").click(function () {
        $("#modal3").hide(); // ID 찾기 모달 숨기기
        $("#modal").show(); // 로그인 모달 다시 보이기
    });

    // 모달 외부 클릭 시 닫기
    $(window).click(function (event) {
        if ($(event.target).is("#modal")) {
            $("#modal").hide(); // 로그인 모달 숨기기
            $("#modal .error").text(''); // 에러 메시지 초기화
        } else if ($(event.target).is("#modal2")) {
            $("#modal2").hide(); // ID 찾기 모달 숨기기
        } else if ($(event.target).is("#modal3")) {
            $("#modal3").hide(); // ID 찾기 모달 숨기기
        }
    });

    // 로그인 폼 제출 처리 (AJAX 요청)
    $("#loginForm").on("submit", function (event) {
        event.preventDefault();

        const loginId = $("#id").val();
        const password = $("#pw").val();

        $.ajax({
            url: "/login",
            type: "POST",
            data: { loginId, password },
            success: function (response) {
                if (response === "success") {
                    // 로그인 성공 시 메인 페이지로 리다이렉트
                    window.location.href = "/main";
                } else {
                    // 로그인 실패 시 에러 메시지 표시
                    $("#errorMsg").text(response).show(); // 에러 메시지를 모달 내 errorMsg에 표시
                    $("#modal").show(); // 로그인 모달 유지
                }
            },
        });
    });

    // ID 찾기 폼 제출 처리 (AJAX 요청)
    $("#findLoginId").submit(function (event) {
        event.preventDefault(); // 폼 기본 제출 동작 막기

        const name = $("#name").val();
        const email = $("#email").val();

        // Ajax 요청 보내기
        fetch("/api/member/findLoginId", {
            method: "POST",
            headers: {
                "Content-Type": "application/x-www-form-urlencoded",
            },
            body: `name=${encodeURIComponent(name)}&email=${encodeURIComponent(email)}`,
        })
            .then((response) => {
                if (response.ok) {
                    return response.text(); // 서버에서 로그인 아이디 반환
                } else {
                    throw new Error("일치하는 정보가 없습니다.");
                }
            })
            .then((loginId) => {
                alert(`로그인 아이디는 '${loginId}'입니다.`);
            })
            .catch((error) => {
                alert(error.message);
            });
    });

    // 임시 비밀번호 발급
    $('#findPw').on('submit', function (event) {
        event.preventDefault();

        const loginId = $('#loginId').val();
        const email = $('#email3').val();

        $.ajax({
            url: '/api/member/findPassword',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({ loginId, email }),
            success: function (response) {
                alert('임시 비밀번호 전송 완료');
                window.location.href = "/main";
            },
            error: function (error) {
                alert('임시 비밀번호 전송 실패');
            }
        });
    });
});