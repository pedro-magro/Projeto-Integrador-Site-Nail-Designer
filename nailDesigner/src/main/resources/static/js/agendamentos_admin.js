document.addEventListener('DOMContentLoaded', function() {
    const agendamentoForm = document.getElementById('agendamento-form');
    const agendamentoIdInput = document.getElementById('agendamento-id');
    const agendamentoClienteSelect = document.getElementById('agendamento-cliente');
    const agendamentoServicoSelect = document.getElementById('agendamento-servico');
    const agendamentoDataInput = document.getElementById('agendamento-data');
    const agendamentoHoraInput = document.getElementById('agendamento-hora');
    const agendamentoStatusSelect = document.getElementById('agendamento-status');
    const agendamentoObservacoesTextarea = document.getElementById('agendamento-observacoes');
    const btnSalvarAgendamento = document.getElementById('btn-salvar-agendamento');
    const btnLimparForm = document.getElementById('btn-limpar-form');
    const listaAgendamentos = document.querySelector('.lista-agendamentos');

    // Dados de exemplo para simulação (em um ambiente real, viriam do backend)
    const clientes = [
        { id: 1, nome: 'Cliente Teste A' },
        { id: 2, nome: 'Cliente Teste B' },
        { id: 3, nome: 'Cliente Teste C' }
    ];

    const servicos = [
        { id: 101, nome: 'Manicure Clássica' },
        { id: 102, nome: 'Alongamento em Gel' },
        { id: 103, nome: 'Pedicure Completa' },
        { id: 104, nome: 'Blindagem de Unhas' }
    ];

    let agendamentosSimulados = [
        {
            id: 1,
            clienteId: 1,
            clienteNome: 'Cliente Teste A',
            servicoId: 101,
            servicoNome: 'Manicure Clássica',
            data: '2025-06-15',
            hora: '10:00',
            status: 'CONFIRMADO',
            observacoes: 'Chegar 10 minutos antes.'
        },
        {
            id: 2,
            clienteId: 2,
            clienteNome: 'Cliente Teste B',
            servicoId: 102,
            servicoNome: 'Alongamento em Gel',
            data: '2025-06-16',
            hora: '14:30',
            status: 'PENDENTE',
            observacoes: ''
        },
        {
            id: 3,
            clienteId: 1,
            clienteNome: 'Cliente Teste A',
            servicoId: 103,
            servicoNome: 'Pedicure Completa',
            data: '2025-06-17',
            hora: '09:00',
            status: 'CONCLUIDO',
            observacoes: 'Pagamento feito em dinheiro.'
        }
    ];

    // --- Funções Auxiliares para Manipulação da UI ---

    // Popula os selects de cliente e serviço
    function popularSelects() {
        // Clientes
        agendamentoClienteSelect.innerHTML = '<option value="">Selecione o Cliente</option>';
        clientes.forEach(cliente => {
            const option = document.createElement('option');
            option.value = cliente.id;
            option.textContent = cliente.nome;
            agendamentoClienteSelect.appendChild(option);
        });

        // Serviços
        agendamentoServicoSelect.innerHTML = '<option value="">Selecione o Serviço</option>';
        servicos.forEach(servico => {
            const option = document.createElement('option');
            option.value = servico.id;
            option.textContent = servico.nome;
            agendamentoServicoSelect.appendChild(option);
        });
    }

    // Função para renderizar todos os agendamentos na lista
    function renderizarAgendamentos() {
        listaAgendamentos.innerHTML = ''; // Limpa a lista existente
        agendamentosSimulados.sort((a, b) => new Date(`${a.data}T${a.hora}`) - new Date(`${b.data}T${b.hora}`)); // Ordena por data/hora
        agendamentosSimulados.forEach(agendamento => addOrUpdateAgendamentoItem(agendamento));
    }

    // Função para adicionar/atualizar um item de agendamento na lista
    function addOrUpdateAgendamentoItem(agendamento) {
        let agendamentoItem = listaAgendamentos.querySelector(`.agendamento-item[data-id="${agendamento.id}"]`);

        if (!agendamentoItem) {
            agendamentoItem = document.createElement('div');
            agendamentoItem.classList.add('agendamento-item');
            listaAgendamentos.appendChild(agendamentoItem);
        }
        
        agendamentoItem.dataset.id = agendamento.id;

        // Formata a data para exibição
        const dataFormatada = new Date(`${agendamento.data}T${agendamento.hora}`).toLocaleDateString('pt-BR', {
            day: '2-digit', month: '2-digit', year: 'numeric'
        });

        // Adiciona classe de status para estilização visual
        let statusClass = '';
        switch (agendamento.status) {
            case 'PENDENTE': statusClass = 'status-pendente'; break;
            case 'CONFIRMADO': statusClass = 'status-confirmado'; break;
            case 'CONCLUIDO': statusClass = 'status-concluido'; break;
            case 'CANCELADO': statusClass = 'status-cancelado'; break;
            default: statusClass = '';
        }

        agendamentoItem.innerHTML = `
            <div class="agendamento-info">
                <h3>${agendamento.servicoNome} com ${agendamento.clienteNome}</h3>
                <p><strong>Cliente:</strong> ${agendamento.clienteNome}</p>
                <p><strong>Serviço:</strong> ${agendamento.servicoNome}</p>
                <p><strong>Data:</strong> ${dataFormatada} às ${agendamento.hora}</p>
                <p><strong>Observações:</strong> ${agendamento.observacoes || 'Nenhuma'}</p>
                <span class="agendamento-meta ${statusClass}">Status: ${agendamento.status}</span>
            </div>
            <div class="item-actions">
                <button class="btn-edit" data-id="${agendamento.id}"><i class="fas fa-edit"></i></button>
                <button class="btn-delete" data-id="${agendamento.id}"><i class="fas fa-trash-alt"></i></button>
            </div>
        `;
    }

    // Função para limpar o formulário e resetar para "Adicionar"
    function limparFormulario() {
        agendamentoForm.reset();
        agendamentoIdInput.value = '';
        btnSalvarAgendamento.textContent = 'Salvar Agendamento';
        btnSalvarAgendamento.innerHTML = '<i class="fas fa-save"></i> Salvar Agendamento';
        btnLimparForm.style.display = 'none';
        // Resetar os selects para a opção padrão se for o caso
        agendamentoClienteSelect.value = '';
        agendamentoServicoSelect.value = '';
        agendamentoStatusSelect.value = 'PENDENTE'; // Status padrão
    }

    // Função para preencher o formulário para edição
    function preencherFormularioParaEdicao(agendamento) {
        agendamentoIdInput.value = agendamento.id;
        agendamentoClienteSelect.value = agendamento.clienteId;
        agendamentoServicoSelect.value = agendamento.servicoId;
        agendamentoDataInput.value = agendamento.data;
        agendamentoHoraInput.value = agendamento.hora;
        agendamentoStatusSelect.value = agendamento.status;
        agendamentoObservacoesTextarea.value = agendamento.observacoes || '';

        btnSalvarAgendamento.textContent = 'Atualizar Agendamento';
        btnSalvarAgendamento.innerHTML = '<i class="fas fa-save"></i> Atualizar Agendamento';
        btnLimparForm.style.display = 'inline-flex';
    }

    // --- Lógica de Eventos ---

    // Inicializa os selects e renderiza os agendamentos ao carregar a página
    popularSelects();
    renderizarAgendamentos();

    // 1. Envio do Formulário (Salvar/Atualizar Agendamento)
    agendamentoForm.addEventListener('submit', function(event) {
        event.preventDefault();

        const clienteSelecionado = clientes.find(c => c.id === parseInt(agendamentoClienteSelect.value));
        const servicoSelecionado = servicos.find(s => s.id === parseInt(agendamentoServicoSelect.value));

        const agendamento = {
            id: agendamentoIdInput.value ? parseInt(agendamentoIdInput.value) : null,
            clienteId: parseInt(agendamentoClienteSelect.value),
            clienteNome: clienteSelecionado ? clienteSelecionado.nome : 'Desconhecido',
            servicoId: parseInt(agendamentoServicoSelect.value),
            servicoNome: servicoSelecionado ? servicoSelecionado.nome : 'Desconhecido',
            data: agendamentoDataInput.value,
            hora: agendamentoHoraInput.value,
            status: agendamentoStatusSelect.value,
            observacoes: agendamentoObservacoesTextarea.value.trim()
        };

        if (agendamento.id) {
            // Lógica de ATUALIZAÇÃO (PUT/PATCH)
            console.log('Atualizando Agendamento:', agendamento);
            // Em um ambiente real, você faria um `fetch` para sua API de backend
            // fetch(`/api/admin/agendamentos/${agendamento.id}`, { method: 'PUT', body: JSON.stringify(agendamento), headers: { 'Content-Type': 'application/json' } })
            // .then(response => response.json()).then(data => { if (data.success) { ... } });
            
            // Simulação de atualização
            setTimeout(() => {
                const index = agendamentosSimulados.findIndex(a => a.id === agendamento.id);
                if (index !== -1) {
                    agendamentosSimulados[index] = agendamento;
                    alert(`Agendamento do cliente "${agendamento.clienteNome}" atualizado com sucesso! (Simulação)`);
                    renderizarAgendamentos(); // Renderiza novamente para refletir a ordem e dados atualizados
                    limparFormulario();
                } else {
                    alert('Erro: Agendamento não encontrado para atualização (Simulação).');
                }
            }, 500);

        } else {
            // Lógica de CRIAÇÃO (POST)
            console.log('Criando Novo Agendamento:', agendamento);
            // Em um ambiente real, você faria um `fetch` para sua API de backend
            // fetch('/api/admin/agendamentos', { method: 'POST', body: JSON.stringify(agendamento), headers: { 'Content-Type': 'application/json' } })
            // .then(response => response.json()).then(data => { if (data.success) { agendamento.id = data.newId; ... } });
            
            // Simulação de criação (atribui um ID temporário)
            setTimeout(() => {
                agendamento.id = Math.max(...agendamentosSimulados.map(a => a.id)) + 1 || 1; // Novo ID
                agendamentosSimulados.push(agendamento);
                alert(`Agendamento para "${agendamento.clienteNome}" adicionado com sucesso! (Simulação)`);
                renderizarAgendamentos(); // Renderiza novamente para incluir o novo agendamento
                limparFormulario();
            }, 500);
        }
    });

    // 2. Botão "Limpar Formulário"
    btnLimparForm.addEventListener('click', limparFormulario);

    // 3. Botões de Edição e Exclusão na Lista (delegação de eventos)
    listaAgendamentos.addEventListener('click', function(event) {
        const target = event.target;
        const editButton = target.closest('.btn-edit');
        const deleteButton = target.closest('.btn-delete');

        if (editButton) {
            const agendamentoId = parseInt(editButton.dataset.id);
            const agendamentoParaEditar = agendamentosSimulados.find(a => a.id === agendamentoId);

            if (agendamentoParaEditar) {
                preencherFormularioParaEdicao(agendamentoParaEditar);
                alert(`Editando agendamento com ID: ${agendamentoId} (Simulação)`);
            } else {
                alert('Agendamento não encontrado para edição.');
            }

        } else if (deleteButton) {
            const agendamentoId = parseInt(deleteButton.dataset.id);
            if (confirm(`Tem certeza que deseja excluir o agendamento com ID ${agendamentoId}?`)) {
                console.log('Excluindo Agendamento com ID:', agendamentoId);
                // Em um ambiente real, você faria um `fetch` para sua API de backend
                // fetch(`/api/admin/agendamentos/${agendamentoId}`, { method: 'DELETE' })
                // .then(response => response.json()).then(data => { if (data.success) { ... } });
                
                // Simulação de exclusão
                setTimeout(() => {
                    agendamentosSimulados = agendamentosSimulados.filter(a => a.id !== agendamentoId);
                    alert(`Agendamento com ID ${agendamentoId} excluído com sucesso! (Simulação)`);
                    renderizarAgendamentos(); // Re-renderiza a lista
                    // Limpar formulário se o item excluído era o que estava sendo editado
                    if (parseInt(agendamentoIdInput.value) === agendamentoId) {
                        limparFormulario();
                    }
                }, 500);
            }
        }
    });
});