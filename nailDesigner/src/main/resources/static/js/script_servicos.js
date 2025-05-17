
function abrirModal() {
    document.getElementById("confirmacaoModal").style.display = "flex";
}

function fecharModal() {
    document.getElementById("confirmacaoModal").style.display = "none";
}

function confirmarAcao() {
    fecharModal();
    window.location.href = "agendamento.html"
}