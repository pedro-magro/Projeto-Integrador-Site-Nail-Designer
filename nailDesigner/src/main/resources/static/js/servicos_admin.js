document.addEventListener('DOMContentLoaded', function() {
    const servicoForm = document.getElementById('servico-form');
    const servicoIdInput = document.getElementById('servico-id');
    const servicoNomeInput = document.getElementById('servico-nome');
    const servicoDescricaoInput = document.getElementById('servico-descricao');
    const servicoValorInput = document.getElementById('servico-valor');
    const servicoDuracaoInput = document.getElementById('servico-duracao');
    const servicoImagemUrlInput = document.getElementById('servico-imagem-url');
    const btnSalvarServico = document.getElementById('btn-salvar-servico');
    const btnLimparForm = document.getElementById('btn-limpar-form');
    const listaServicos = document.querySelector('.lista-servicos');

    // --- Funções Auxiliares para Manipulação da UI ---

    // Função para adicionar/atualizar um item de serviço na lista
    function addOrUpdateServicoItem(servico) {
        let servicoItem = listaServicos.querySelector(`.servico-item[data-id="${servico.id}"]`);

        if (!servicoItem) {
            // Se não encontrou, cria um novo item
            servicoItem = document.createElement('div');
            servicoItem.classList.add('servico-item');
            listaServicos.appendChild(servicoItem);
        }
        
        servicoItem.dataset.id = servico.id; // Garante que o data-id esteja atualizado

        // Formata o valor para moeda BRL
        const valorFormatado = new Intl.NumberFormat('pt-BR', {
            style: 'currency',
            currency: 'BRL'
        }).format(servico.valor);

        servicoItem.innerHTML = `
            <img src="${servico.imagemUrl || '/img/placeholder-servico.jpg'}" alt="${servico.nome}">
            <div class="servico-info">
                <h3>${servico.nome}</h3>
                <p>${servico.descricao}</p>
                <span class="servico-meta">Valor: ${valorFormatado} | Duração: ${servico.duracao} min</span>
            </div>
            <div class="item-actions">
                <button class="btn-edit" data-id="${servico.id}"><i class="fas fa-edit"></i></button>
                <button class="btn-delete" data-id="${servico.id}"><i class="fas fa-trash-alt"></i></button>
            </div>
        `;
    }

    // Função para limpar o formulário e resetar para "Adicionar"
    function limparFormulario() {
        servicoForm.reset();
        servicoIdInput.value = ''; // Limpa o ID oculto
        btnSalvarServico.textContent = 'Salvar Serviço';
        btnSalvarServico.innerHTML = '<i class="fas fa-save"></i> Salvar Serviço';
        btnLimparForm.style.display = 'none'; // Esconde o botão Limpar
    }

    // Função para preencher o formulário para edição
    function preencherFormularioParaEdicao(servico) {
        servicoIdInput.value = servico.id;
        servicoNomeInput.value = servico.nome;
        servicoDescricaoInput.value = servico.descricao;
        servicoValorInput.value = servico.valor;
        servicoDuracaoInput.value = servico.duracao;
        servicoImagemUrlInput.value = servico.imagemUrl || '';

        btnSalvarServico.textContent = 'Atualizar Serviço';
        btnSalvarServico.innerHTML = '<i class="fas fa-save"></i> Atualizar Serviço';
        btnLimparForm.style.display = 'inline-flex'; // Mostra o botão Limpar
    }

    // --- Lógica de Eventos ---

    // 1. Envio do Formulário (Salvar/Atualizar Serviço)
    servicoForm.addEventListener('submit', function(event) {
        event.preventDefault();

        const servico = {
            id: servicoIdInput.value ? parseInt(servicoIdInput.value) : null,
            nome: servicoNomeInput.value,
            descricao: servicoDescricaoInput.value,
            valor: parseFloat(servicoValorInput.value),
            duracao: parseInt(servicoDuracaoInput.value),
            imagemUrl: servicoImagemUrlInput.value
        };

        if (servico.id) {
            // Lógica de ATUALIZAÇÃO (PUT/PATCH)
            console.log('Atualizando Serviço:', servico);
            // Em produção: fetch(`/api/admin/servicos/${servico.id}`, { method: 'PUT', body: JSON.stringify(servico), headers: { 'Content-Type': 'application/json' } })
            // .then(response => response.json()).then(data => { if (data.success) { addOrUpdateServicoItem(servico); limparFormulario(); alert('Serviço atualizado!'); } else { alert('Erro ao atualizar: ' + data.message); } });
            
            // Simulação de atualização
            setTimeout(() => {
                alert(`Serviço "${servico.nome}" atualizado com sucesso! (Simulação)`);
                addOrUpdateServicoItem(servico); // Atualiza o item na UI
                limparFormulario();
            }, 500);

        } else {
            // Lógica de CRIAÇÃO (POST)
            console.log('Criando Novo Serviço:', servico);
            // Em produção: fetch('/api/admin/servicos', { method: 'POST', body: JSON.stringify(servico), headers: { 'Content-Type': 'application/json' } })
            // .then(response => response.json()).then(data => { if (data.success) { servico.id = data.newId; addOrUpdateServicoItem(servico); limparFormulario(); alert('Serviço adicionado!'); } else { alert('Erro ao adicionar: ' + data.message); } });
            
            // Simulação de criação (atribui um ID temporário)
            setTimeout(() => {
                servico.id = Math.floor(Math.random() * 1000) + 1; // ID aleatório para simulação
                alert(`Serviço "${servico.nome}" adicionado com sucesso! (Simulação)`);
                addOrUpdateServicoItem(servico); // Adiciona o novo item na UI
                limparFormulario();
            }, 500);
        }
    });

    // 2. Botão "Limpar Formulário"
    btnLimparForm.addEventListener('click', limparFormulario);

    // 3. Botões de Edição e Exclusão na Lista (delegação de eventos)
    listaServicos.addEventListener('click', function(event) {
        const target = event.target;
        const editButton = target.closest('.btn-edit');
        const deleteButton = target.closest('.btn-delete');

        if (editButton) {
            const servicoId = editButton.dataset.id;
            // Em produção: buscar detalhes do serviço pelo ID do backend
            // fetch(`/api/admin/servicos/${servicoId}`).then(response => response.json()).then(servicoData => {
            //     preencherFormularioParaEdicao(servicoData);
            // });

            // Simulação de busca para edição
            const servicoParaEditar = {
                id: servicoId,
                nome: "Serviço Editado " + servicoId,
                descricao: "Descrição editada para demonstração.",
                valor: 150.00,
                duracao: 75,
                imagemUrl: "/img/unha-3.jpg" // Imagem de exemplo para edição
            };
            preencherFormularioParaEdicao(servicoParaEditar);
            alert(`Editando serviço com ID: ${servicoId} (Simulação)`);

        } else if (deleteButton) {
            const servicoId = deleteButton.dataset.id;
            if (confirm(`Tem certeza que deseja excluir o serviço com ID ${servicoId}?`)) {
                console.log('Excluindo Serviço com ID:', servicoId);
                // Em produção: fetch(`/api/admin/servicos/${servicoId}`, { method: 'DELETE' })
                // .then(response => response.json()).then(data => { if (data.success) { deleteButton.closest('.servico-item').remove(); alert('Serviço excluído!'); } else { alert('Erro ao excluir: ' + data.message); } });
                
                // Simulação de exclusão
                setTimeout(() => {
                    alert(`Serviço com ID ${servicoId} excluído com sucesso! (Simulação)`);
                    deleteButton.closest('.servico-item').remove(); // Remove da UI
                    // Limpar formulário se o item excluído era o que estava sendo editado
                    if (servicoIdInput.value === servicoId) {
                        limparFormulario();
                    }
                }, 500);
            }
        }
    });
});