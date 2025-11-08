// SOSEngasgo - User Management JavaScript
class UserManager {
    constructor() {
        this.apiUrl = "/gestao/usuarios";
        this.currentPage = window.location.pathname;
        this.init();
    }

    init() {
        // Se estiver na página de lista, carrega os usuários
        if (this.currentPage.includes('/usuarios') && !this.currentPage.includes('/novo') && !this.currentPage.includes('/editar')) {
            this.carregarUsuarios();
        }
        
        // Se estiver na página de form, configura o formulário
        if (this.currentPage.includes('/novo') || this.currentPage.includes('/editar')) {
            this.setupForm();
            this.carregarUsuarioParaEdicao();
        }
    }

    setupForm() {
        const form = document.getElementById("userForm");
        if (!form) return;

        form.addEventListener("submit", (e) => this.handleSubmit(e));
        
        // Validação em tempo real
        const inputs = form.querySelectorAll('input, select');
        inputs.forEach(input => {
            input.addEventListener('blur', () => this.validateField(input));
            input.addEventListener('input', () => this.clearFieldError(input));
        });

        // Máscara de telefone
        const telefoneInput = document.getElementById('telefone');
        if (telefoneInput) {
            telefoneInput.addEventListener('input', (e) => this.maskTelefone(e));
        }
    }

    maskTelefone(e) {
        let value = e.target.value.replace(/\D/g, '');
        if (value.length > 11) value = value.slice(0, 11);
        
        if (value.length > 6) {
            value = value.replace(/^(\d{2})(\d{5})(\d{0,4}).*/, '($1) $2-$3');
        } else if (value.length > 2) {
            value = value.replace(/^(\d{2})(\d{0,5})/, '($1) $2');
        } else if (value.length > 0) {
            value = value.replace(/^(\d*)/, '($1');
        }
        
        e.target.value = value;
    }

    validateField(field) {
        const value = field.value.trim();
        const isValid = field.checkValidity();
        
        if (!isValid || (!value && field.required)) {
            field.classList.add('is-invalid');
            field.classList.remove('is-valid');
        } else if (value || !field.required) {
            field.classList.add('is-valid');
            field.classList.remove('is-invalid');
        }
        
        return isValid;
    }

    clearFieldError(field) {
        field.classList.remove('is-invalid');
        if (field.value.trim()) {
            field.classList.add('is-valid');
        } else {
            field.classList.remove('is-valid');
        }
    }

    showAlert(message, type = 'success') {
        const alertContainer = document.getElementById('alertContainer');
        if (!alertContainer) return;
        
        const alertClass = type === 'success' ? 'alert-success' : 'alert-danger';
        const icon = type === 'success' ? 'check-circle' : 'exclamation-triangle';
        
        alertContainer.innerHTML = `
            <div class="alert ${alertClass} alert-dismissible fade show" role="alert">
                <i class="fas fa-${icon} me-2"></i>
                ${message}
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
        `;
        
        // Scroll to top para ver o alerta
        window.scrollTo({ top: 0, behavior: 'smooth' });
        
        // Auto dismiss após 5 segundos
        setTimeout(() => {
            const alert = alertContainer.querySelector('.alert');
            if (alert) {
                alert.remove();
            }
        }, 5000);
    }

    setLoadingState(button, loading = true) {
        if (loading) {
            button.disabled = true;
            button.dataset.originalText = button.innerHTML;
            button.innerHTML = '<i class="fas fa-spinner fa-spin me-2"></i>Processando...';
        } else {
            button.disabled = false;
            button.innerHTML = button.dataset.originalText;
        }
    }

    async carregarUsuarios() {
        const tableBody = document.getElementById('userTable');
        if (!tableBody) return;

        try {
            const response = await fetch(`${this.apiUrl}/listar`);
            if (!response.ok) throw new Error(`HTTP ${response.status}`);
            
            const usuarios = await response.json();
            this.renderTable(usuarios);
            
        } catch (error) {
            console.error("Erro ao carregar usuários:", error);
            this.showAlert("Erro ao carregar usuários. Verifique a conexão com o servidor.", "error");
            this.renderEmptyState("Erro ao carregar dados");
        }
    }

    renderTable(usuarios) {
        const tableBody = document.getElementById('userTable');
        if (!tableBody) return;

        if (!usuarios || usuarios.length === 0) {
            this.renderEmptyState("Nenhum usuário cadastrado");
            return;
        }

        tableBody.innerHTML = usuarios.map(user => {
            const email = user.contatoUsuario?.email || 'N/A';
            const capacitado = user.capacitadoLeiLucas 
                ? '<span class="badge bg-success"><i class="fa fa-check"></i> Sim</span>' 
                : '<span class="badge bg-secondary">Não</span>';
            
            return `
                <tr class="fade-in">
                    <td>
                        <div class="d-flex align-items-center">
                            <div class="me-3">
                                <i class="fas fa-user-circle fa-2x text-secondary"></i>
                            </div>
                            <div>
                                <strong>${this.escapeHtml(user.nome)}</strong>
                            </div>
                        </div>
                    </td>
                    <td>
                        <span class="text-muted">
                            <i class="fas fa-envelope me-1"></i>
                            ${this.escapeHtml(email)}
                        </span>
                    </td>
                    <td>
                        <span class="badge bg-info">${this.escapeHtml(user.tipo || 'N/A')}</span>
                    </td>
                    <td>
                        <span class="badge bg-primary">${this.escapeHtml(user.perfilAcesso || 'N/A')}</span>
                    </td>
                    <td class="text-center">
                        ${capacitado}
                    </td>
                    <td class="text-center">
                        <div class="btn-group" role="group">
                            <a href="/gestao/usuarios/editar/${user.id}" class="btn btn-sm btn-outline-primary" title="Editar">
                                <i class="fas fa-edit"></i>
                            </a>
                            <button class="btn btn-sm btn-outline-danger" 
                                    onclick="userManager.deletarUsuario('${user.id}')" 
                                    title="Excluir">
                                <i class="fas fa-trash"></i>
                            </button>
                        </div>
                    </td>
                </tr>
            `;
        }).join('');
    }

    renderEmptyState(message) {
        const tableBody = document.getElementById('userTable');
        if (!tableBody) return;
        
        tableBody.innerHTML = `
            <tr>
                <td colspan="6" class="text-center py-5">
                    <i class="fas fa-users fa-3x text-muted mb-3"></i>
                    <p class="text-muted mb-0">${message}</p>
                </td>
            </tr>
        `;
    }

    escapeHtml(text) {
        if (!text) return '';
        const div = document.createElement('div');
        div.textContent = text;
        return div.innerHTML;
    }

    async carregarUsuarioParaEdicao() {
        const pathParts = window.location.pathname.split('/');
        const editIndex = pathParts.indexOf('editar');
        
        console.log('🔍 Carregando usuário para edição...');
        console.log('Path parts:', pathParts);
        console.log('Edit index:', editIndex);
        
        if (editIndex === -1 || !pathParts[editIndex + 1]) return;
        
        const userId = pathParts[editIndex + 1];
        console.log('📋 ID do usuário:', userId);
        
        try {
            const url = `${this.apiUrl}/${userId}`;
            console.log('🌐 Fazendo requisição para:', url);
            
            const response = await fetch(url);
            console.log('📥 Response status:', response.status);
            
            if (!response.ok) throw new Error(`HTTP ${response.status}`);
            
            const usuario = await response.json();
            console.log('✅ Usuário carregado:', usuario);
            
            this.preencherFormulario(usuario);
            
        } catch (error) {
            console.error("❌ Erro ao carregar usuário:", error);
            this.showAlert("Erro ao carregar dados do usuário.", "error");
        }
    }

    preencherFormulario(usuario) {
        console.log('📝 Preenchendo formulário com:', usuario);
        document.getElementById('userId').value = usuario.id || '';
        document.getElementById('nome').value = usuario.nome || '';
        document.getElementById('tipo').value = usuario.tipo || '';
        document.getElementById('perfilAcesso').value = usuario.perfilAcesso || '';
        document.getElementById('faixaEtariaAtendimento').value = usuario.faixaEtariaAtendimento || '';
        document.getElementById('escolaId').value = usuario.escolaId || '';
        document.getElementById('capacitadoLeiLucas').checked = usuario.capacitadoLeiLucas || false;
        
        if (usuario.contatoUsuario) {
            document.getElementById('email').value = usuario.contatoUsuario.email || '';
            document.getElementById('telefone').value = usuario.contatoUsuario.telefone || '';
        }
        
        // Mostrar mensagem que senha é opcional na edição
        const senhaOptional = document.getElementById('senhaOptional');
        if (senhaOptional) {
            senhaOptional.style.display = 'inline';
        }
        
        // Remover required da senha na edição
        const senhaInput = document.getElementById('senha');
        if (senhaInput) {
            senhaInput.removeAttribute('required');
        }
    }

    async handleSubmit(e) {
        e.preventDefault();
        
        const form = e.target;
        const submitBtn = form.querySelector('button[type="submit"]');
        
        // Validar formulário
        if (!form.checkValidity()) {
            e.stopPropagation();
            form.classList.add('was-validated');
            return;
        }

        const userId = document.getElementById("userId").value;
        const senha = document.getElementById("senha").value;
        
        // Construir objeto usuario conforme o modelo
        const usuario = {
            nome: document.getElementById("nome").value.trim(),
            tipo: document.getElementById("tipo").value,
            faixaEtariaAtendimento: document.getElementById("faixaEtariaAtendimento").value || null,
            escolaId: document.getElementById("escolaId").value || null,
            perfilAcesso: document.getElementById("perfilAcesso").value,
            capacitadoLeiLucas: document.getElementById("capacitadoLeiLucas").checked,
            contatoUsuario: {
                email: document.getElementById("email").value.trim(),
                telefone: document.getElementById("telefone").value.replace(/\D/g, '') || null
            }
        };
        
        // Adicionar senha apenas se foi preenchida
        if (senha) {
            usuario.senha = senha;
        }

        try {
            this.setLoadingState(submitBtn, true);

            let response;
            if (userId) {
                // Atualizar
                response = await fetch(`${this.apiUrl}/${userId}`, {
                    method: "PUT",
                    headers: { "Content-Type": "application/json" },
                    body: JSON.stringify(usuario)
                });
            } else {
                // Criar novo - senha é obrigatória
                if (!senha) {
                    this.showAlert("A senha é obrigatória para novos usuários.", "error");
                    this.setLoadingState(submitBtn, false);
                    return;
                }
                
                response = await fetch(`${this.apiUrl}/salvar`, {
                    method: "POST",
                    headers: { "Content-Type": "application/json" },
                    body: JSON.stringify(usuario)
                });
            }

            if (!response.ok) {
                throw new Error(`HTTP ${response.status}: ${response.statusText}`);
            }

            this.showAlert(
                userId ? "Usuário atualizado com sucesso!" : "Usuário criado com sucesso!",
                "success"
            );

            // Redirecionar para lista após 1.5 segundos
            setTimeout(() => {
                window.location.href = '/gestao/usuarios';
            }, 1500);

        } catch (error) {
            console.error("Erro ao salvar usuário:", error);
            this.showAlert("Erro ao salvar usuário. Tente novamente.", "error");
            this.setLoadingState(submitBtn, false);
        }
    }

    async deletarUsuario(id) {
        if (!confirm("Tem certeza que deseja excluir este usuário?")) return;

        try {
            const response = await fetch(`${this.apiUrl}/${id}`, { 
                method: "DELETE" 
            });

            if (!response.ok) {
                throw new Error(`HTTP ${response.status}`);
            }

            this.showAlert("Usuário excluído com sucesso!", "success");
            await this.carregarUsuarios();

        } catch (error) {
            console.error("Erro ao deletar usuário:", error);
            this.showAlert("Erro ao excluir usuário. Tente novamente.", "error");
        }
    }
}

// Função de filtro para pesquisa
function filtrarUsuarios() {
    const searchInput = document.getElementById('searchInput');
    if (!searchInput) return;
    
    const searchTerm = searchInput.value.toLowerCase();
    const tableRows = document.querySelectorAll('#userTable tr');
    
    tableRows.forEach(row => {
        const text = row.textContent.toLowerCase();
        row.style.display = text.includes(searchTerm) ? '' : 'none';
    });
}

// Inicializar quando DOM estiver pronto
document.addEventListener('DOMContentLoaded', () => {
    window.userManager = new UserManager();
    
    // Adicionar listener no campo de pesquisa se existir
    const searchInput = document.getElementById('searchInput');
    if (searchInput) {
        searchInput.addEventListener('input', filtrarUsuarios);
    }
});
