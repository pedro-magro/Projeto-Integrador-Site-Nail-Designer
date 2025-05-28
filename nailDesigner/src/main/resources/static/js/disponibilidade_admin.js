document.addEventListener('DOMContentLoaded', function() {
    const bloqueioForm = document.getElementById('bloqueio-form');
    const listaBloqueios = document.querySelector('.lista-bloqueios');

    // Função para adicionar um item de bloqueio à lista (para demonstração)
    function addBloqueioItem(data, inicio, fim, motivo) {
        const newItem = document.createElement('div');
        newItem.classList.add('disponibilidade-item', 'bloqueio-item'); // Adiciona a classe bloqueio-item para estilização
        const periodo = (inicio && fim) ? `${inicio} - ${fim}` : 'Dia Inteiro';
        const motivoTexto = motivo ? ` - ${motivo}` : '';
        newItem.innerHTML = `
            <span><strong>${data}:</strong> ${periodo}${motivoTexto}</span>
            <div class="item-actions">
                <button class="btn-delete"><i class="fas fa-trash-alt"></i></button>
            </div>
        `;
        listaBloqueios.appendChild(newItem);
    }

    // --- Lógica para o formulário de Bloqueio Específico ---
    bloqueioForm.addEventListener('submit', function(event) {
        event.preventDefault();

        const dataBloqueio = document.getElementById('data-bloqueio').value;
        const horaInicioBloqueio = document.getElementById('hora-inicio-bloqueio').value;
        const horaFimBloqueio = document.getElementById('hora-fim-bloqueio').value;
        const motivoBloqueio = document.getElementById('motivo-bloqueio').value;

        if (dataBloqueio) {
            // Em produção: Envie os dados para o backend (API REST)
            // Ex: fetch('/api/admin/bloqueios', {
            //     method: 'POST',
            //     headers: { 'Content-Type': 'application/json' },
            //     body: JSON.stringify({ dataBloqueio, horaInicioBloqueio, horaFimBloqueio, motivoBloqueio })
            // })
            // .then(response => response.json())
            // .then(data => {
            //     if (data.success) {
            //         alert('Período bloqueado com sucesso!');
            //         // Adiciona o item na lista se o backend confirmar
            //         addBloqueioItem(dataBloqueio, horaInicioBloqueio, horaFimBloqueio, motivoBloqueio);
            //         bloqueioForm.reset(); // Limpa o formulário
            //     } else {
            //         alert('Erro ao bloquear período: ' + data.message);
            //     }
            // })
            // .catch(error => console.error('Erro:', error));

            // Simulação de sucesso para demonstração:
            console.log('Dados Bloqueio Específico:', { dataBloqueio, horaInicioBloqueio, horaFimBloqueio, motivoBloqueio });
            alert('Período bloqueado com sucesso! (Simulação)');
            addBloqueioItem(dataBloqueio, horaInicioBloqueio, horaFimBloqueio, motivoBloqueio);
            bloqueioForm.reset(); // Limpa o formulário
        } else {
            alert('Por favor, selecione a Data do Bloqueio.');
        }
    });

    // --- Lógica para botões de Excluir Bloqueios (delegation de eventos) ---
    listaBloqueios.addEventListener('click', function(event) {
        const target = event.target;
        const deleteButton = target.closest('.btn-delete');
        const item = target.closest('.bloqueio-item');

        if (!item) return;

        if (deleteButton) {
            if (confirm('Tem certeza que deseja remover este bloqueio?')) {
                alert('Funcionalidade de Remover Bloqueio clicada para: ' + item.querySelector('span').textContent);
                item.remove(); // Remove o item da UI
                // Aqui você enviaria o ID do bloqueio para o backend para exclusão
                // Ex: fetch(`/api/admin/bloqueios/${idDoBloqueio}`, { method: 'DELETE' })
            }
        }
    });
});