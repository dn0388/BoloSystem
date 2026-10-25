const $=id=>document.getElementById(id);const API={produtos:"/api/produtos",clientes:"/api/clientes",pedidos:"/api/pedidos"};
function msg(text){$("mensagem").textContent=text;$("mensagem").className=text?"message":"";}
async function req(url,options={}){const r=await fetch(url,{headers:{"Content-Type":"application/json",...(options.headers||{})},...options});let body=null;const type=r.headers.get("content-type")||"";if(type.includes("json"))body=await r.json();else body=await r.text();if(!r.ok)throw new Error(typeof body==="string"?body:(body?.erro||body?.message||`Erro HTTP ${r.status}`));return body;}
function money(v){return Number(v).toLocaleString("pt-BR",{style:"currency",currency:"BRL"});}
function cell(row,value){const td=document.createElement("td");td.textContent=value??"";row.append(td);return td;}
function actionButton(text,fn,secondary=false){const b=document.createElement("button");b.type="button";b.textContent=text;if(secondary)b.className="secondary";b.addEventListener("click",fn);return b;}
async function carregarProdutos() {
  const rows = $("produtosLista");
  rows.replaceChildren();
  try {
    const items = await req(API.produtos);
    items.forEach(p => {
      const tr = document.createElement("tr");
      cell(tr, p.id); cell(tr, p.nome); cell(tr, money(p.preco));
      const td = cell(tr, "");
      const editar = actionButton("Editar", () => {
        $("produtoId").value = p.id;
        $("produtoNome").value = p.nome;
        $("produtoPreco").value = p.preco;
        $("produtoTitulo").textContent = "Editar produto";
        document.querySelector('[data-tab="produtos"]').click();
        window.scrollTo({ top: 0, behavior: "smooth" });
      }, true);
      const excluir = actionButton("Excluir", async () => {
        if (!confirm(`Excluir o produto ${p.nome}?`)) return;
        try {
          await req(`${API.produtos}/${p.id}`, { method: "DELETE" });
          msg("Produto excluído.");
          await carregarProdutos();
          await preencherSelects();
        } catch (e) { msg(e.message); }
      }, true);
      td.append(editar, excluir);
      rows.append(tr);
    });
    if (!items.length) {
      const tr = document.createElement("tr");
      const td = cell(tr, "Nenhum produto cadastrado."); td.colSpan = 4; rows.append(tr);
    }
    await preencherProdutosSelect(items);
  } catch (e) { msg(`Produtos: ${e.message}`); }
}
function limparProduto(){$("produtoForm").reset();$("produtoId").value="";$("produtoTitulo").textContent="Cadastrar produto";}
$("produtoForm").addEventListener("submit",async e=>{e.preventDefault();const id=$("produtoId").value;const nome=$("produtoNome").value.trim();const preco=Number($("produtoPreco").value);if(!nome||$("produtoPreco").value===""||!Number.isFinite(preco)||preco<0){msg("Informe nome e preço válido.");return;}try{await req(id?`${API.produtos}/${id}`:API.produtos,{method:id?"PUT":"POST",body:JSON.stringify({nome,preco})});msg("Produto salvo com sucesso.");limparProduto();await carregarProdutos();}catch(e){msg(e.message);}});
async function carregarClientes() {
  const rows = $("clientesLista");
  rows.replaceChildren();
  try {
    const items = await req(API.clientes);
    items.forEach(c => {
      const tr = document.createElement("tr");
      cell(tr, c.id); cell(tr, c.nome); cell(tr, c.contato);
      const td = cell(tr, "");
      const editar = actionButton("Editar", () => {
        $("clienteId").value = c.id;
        $("clienteNome").value = c.nome || "";
        $("clienteContato").value = c.contato || "";
        $("clienteCpf").value = c.cpf || "";
        $("clienteEndereco").value = c.endereco || "";
        $("clienteReferencia").value = c.pontoReferencia || "";
        $("clienteTitulo").textContent = "Editar cliente";
        document.querySelector('[data-tab="clientes"]').click();
        window.scrollTo({ top: 0, behavior: "smooth" });
      }, true);
      const excluir = actionButton("Excluir", async () => {
        if (!confirm(`Excluir cliente ${c.nome}?`)) return;
        try {
          await req(`${API.clientes}/${c.id}`, { method: "DELETE" });
          msg("Cliente excluído."); await carregarClientes();
        } catch (e) { msg(e.message); }
      }, true);
      td.append(editar, excluir); rows.append(tr);
    });
    if (!items.length) {
      const tr = document.createElement("tr");
      const td = cell(tr, "Nenhum cliente cadastrado."); td.colSpan = 4; rows.append(tr);
    }
    await preencherClientesSelect(items);
  } catch (e) { msg(`Clientes: ${e.message}`); }
}
function limparCliente(){$("clienteForm").reset();$("clienteId").value="";$("clienteTitulo").textContent="Cadastrar cliente";}
$("clienteForm").addEventListener("submit",async e=>{e.preventDefault();const id=$("clienteId").value;const cliente={nome:$("clienteNome").value.trim(),contato:$("clienteContato").value.trim(),cpf:$("clienteCpf").value.trim(),endereco:$("clienteEndereco").value.trim(),pontoReferencia:$("clienteReferencia").value.trim()};if(!cliente.nome){msg("Nome do cliente é obrigatório.");return;}try{await req(id?`${API.clientes}/${id}`:API.clientes,{method:id?"PUT":"POST",body:JSON.stringify(cliente)});msg("Cliente salvo com sucesso.");limparCliente();await carregarClientes();}catch(e){msg(e.message);}});
async function preencherProdutosSelect(items){const s=$("pedidoProduto");const current=s.value;s.replaceChildren(new Option("Selecione um produto",""));items.forEach(p=>s.add(new Option(`${p.nome} — ${money(p.preco)}`,p.id)));if(current)s.value=current;}
async function preencherClientesSelect(items){const s=$("pedidoCliente");const current=s.value;s.replaceChildren(new Option("Selecione um cliente",""));items.forEach(c=>s.add(new Option(c.nome,c.id)));if(current)s.value=current;}
async function preencherSelects(){try{await Promise.all([req(API.produtos).then(preencherProdutosSelect),req(API.clientes).then(preencherClientesSelect)]);}catch(e){/* Mensagem detalhada aparece nas abas próprias. */}}
$("pedidoForm").addEventListener("submit",async e=>{e.preventDefault();const clienteId=Number($("pedidoCliente").value),produtoId=Number($("pedidoProduto").value),quantidade=Number($("pedidoQuantidade").value);if(!clienteId||!produtoId||!Number.isInteger(quantidade)||quantidade<1){msg("Selecione cliente, produto e quantidade válida.");return;}try{const result=await req(API.pedidos,{method:"POST",body:JSON.stringify({clienteId,itens:[{produtoId,quantidade,subtotal:0}]})});msg(typeof result==="string"?result:"Pedido registrado com sucesso.");$("pedidoForm").reset();$("pedidoQuantidade").value=1;}catch(e){msg(e.message);}});
$("produtoLimpar").addEventListener("click",limparProduto);$("clienteLimpar").addEventListener("click",limparCliente);$("produtosAtualizar").addEventListener("click",carregarProdutos);$("clientesAtualizar").addEventListener("click",carregarClientes);$("temaBtn").addEventListener("click",()=>document.body.classList.toggle("escuro"));
document.querySelectorAll(".tab").forEach(b=>b.addEventListener("click",()=>{document.querySelectorAll(".tab").forEach(x=>x.classList.toggle("active",x===b));document.querySelectorAll(".painel").forEach(p=>p.classList.toggle("active",p.id===b.dataset.tab));if(b.dataset.tab==="produtos")carregarProdutos();if(b.dataset.tab==="clientes")carregarClientes();if(b.dataset.tab==="pedidos")preencherSelects();}));
carregarProdutos();carregarClientes();
