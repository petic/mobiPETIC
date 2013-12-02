// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: dadosiniciais.proto

package br.ufs.dcomp.gpes.peticwizard.presentation.MobiPETIC;

public final class DadosIniciais {
	private DadosIniciais() {
	}

	public static void registerAllExtensions(
			com.google.protobuf.ExtensionRegistry registry) {
	}

	public interface UsuarioOrBuilder extends
			com.google.protobuf.MessageOrBuilder {

		// required int32 id = 1;
		boolean hasId();

		int getId();

		// required int32 empresa_id = 2;
		boolean hasEmpresaId();

		int getEmpresaId();

		// optional bytes empresa_logo = 3;
		boolean hasEmpresaLogo();

		com.google.protobuf.ByteString getEmpresaLogo();
	}

	public static final class Usuario extends
			com.google.protobuf.GeneratedMessage implements UsuarioOrBuilder {
		// Use Usuario.newBuilder() to construct.
		private Usuario(Builder builder) {
			super(builder);
		}

		private Usuario(boolean noInit) {
		}

		private static final Usuario defaultInstance;

		public static Usuario getDefaultInstance() {
			return defaultInstance;
		}

		public Usuario getDefaultInstanceForType() {
			return defaultInstance;
		}

		public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
			return br.ufs.dcomp.gpes.peticwizard.presentation.MobiPETIC.DadosIniciais.internal_static_MobiPETIC_Usuario_descriptor;
		}

		@Override
		protected com.google.protobuf.GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
			return br.ufs.dcomp.gpes.peticwizard.presentation.MobiPETIC.DadosIniciais.internal_static_MobiPETIC_Usuario_fieldAccessorTable;
		}

		private int bitField0_;
		// required int32 id = 1;
		public static final int ID_FIELD_NUMBER = 1;
		private int id_;

		public boolean hasId() {
			return ((bitField0_ & 0x00000001) == 0x00000001);
		}

		public int getId() {
			return id_;
		}

		// required int32 empresa_id = 2;
		public static final int EMPRESA_ID_FIELD_NUMBER = 2;
		private int empresaId_;

		public boolean hasEmpresaId() {
			return ((bitField0_ & 0x00000002) == 0x00000002);
		}

		public int getEmpresaId() {
			return empresaId_;
		}

		// optional bytes empresa_logo = 3;
		public static final int EMPRESA_LOGO_FIELD_NUMBER = 3;
		private com.google.protobuf.ByteString empresaLogo_;

		public boolean hasEmpresaLogo() {
			return ((bitField0_ & 0x00000004) == 0x00000004);
		}

		public com.google.protobuf.ByteString getEmpresaLogo() {
			return empresaLogo_;
		}

		private void initFields() {
			id_ = 0;
			empresaId_ = 0;
			empresaLogo_ = com.google.protobuf.ByteString.EMPTY;
		}

		private byte memoizedIsInitialized = -1;

		@Override
		public final boolean isInitialized() {
			byte isInitialized = memoizedIsInitialized;
			if (isInitialized != -1)
				return isInitialized == 1;

			if (!hasId()) {
				memoizedIsInitialized = 0;
				return false;
			}
			if (!hasEmpresaId()) {
				memoizedIsInitialized = 0;
				return false;
			}
			memoizedIsInitialized = 1;
			return true;
		}

		@Override
		public void writeTo(com.google.protobuf.CodedOutputStream output)
				throws java.io.IOException {
			getSerializedSize();
			if (((bitField0_ & 0x00000001) == 0x00000001)) {
				output.writeInt32(1, id_);
			}
			if (((bitField0_ & 0x00000002) == 0x00000002)) {
				output.writeInt32(2, empresaId_);
			}
			if (((bitField0_ & 0x00000004) == 0x00000004)) {
				output.writeBytes(3, empresaLogo_);
			}
			getUnknownFields().writeTo(output);
		}

		private int memoizedSerializedSize = -1;

		@Override
		public int getSerializedSize() {
			int size = memoizedSerializedSize;
			if (size != -1)
				return size;

			size = 0;
			if (((bitField0_ & 0x00000001) == 0x00000001)) {
				size += com.google.protobuf.CodedOutputStream.computeInt32Size(
						1, id_);
			}
			if (((bitField0_ & 0x00000002) == 0x00000002)) {
				size += com.google.protobuf.CodedOutputStream.computeInt32Size(
						2, empresaId_);
			}
			if (((bitField0_ & 0x00000004) == 0x00000004)) {
				size += com.google.protobuf.CodedOutputStream.computeBytesSize(
						3, empresaLogo_);
			}
			size += getUnknownFields().getSerializedSize();
			memoizedSerializedSize = size;
			return size;
		}

		private static final long serialVersionUID = 0L;

		@java.lang.Override
		protected java.lang.Object writeReplace()
				throws java.io.ObjectStreamException {
			return super.writeReplace();
		}

		public static br.ufs.dcomp.gpes.peticwizard.presentation.MobiPETIC.DadosIniciais.Usuario parseFrom(
				com.google.protobuf.ByteString data)
				throws com.google.protobuf.InvalidProtocolBufferException {
			return newBuilder().mergeFrom(data).buildParsed();
		}

		public static br.ufs.dcomp.gpes.peticwizard.presentation.MobiPETIC.DadosIniciais.Usuario parseFrom(
				com.google.protobuf.ByteString data,
				com.google.protobuf.ExtensionRegistryLite extensionRegistry)
				throws com.google.protobuf.InvalidProtocolBufferException {
			return newBuilder().mergeFrom(data, extensionRegistry)
					.buildParsed();
		}

		public static br.ufs.dcomp.gpes.peticwizard.presentation.MobiPETIC.DadosIniciais.Usuario parseFrom(
				byte[] data)
				throws com.google.protobuf.InvalidProtocolBufferException {
			return newBuilder().mergeFrom(data).buildParsed();
		}

		public static br.ufs.dcomp.gpes.peticwizard.presentation.MobiPETIC.DadosIniciais.Usuario parseFrom(
				byte[] data,
				com.google.protobuf.ExtensionRegistryLite extensionRegistry)
				throws com.google.protobuf.InvalidProtocolBufferException {
			return newBuilder().mergeFrom(data, extensionRegistry)
					.buildParsed();
		}

		public static br.ufs.dcomp.gpes.peticwizard.presentation.MobiPETIC.DadosIniciais.Usuario parseFrom(
				java.io.InputStream input) throws java.io.IOException {
			return newBuilder().mergeFrom(input).buildParsed();
		}

		public static br.ufs.dcomp.gpes.peticwizard.presentation.MobiPETIC.DadosIniciais.Usuario parseFrom(
				java.io.InputStream input,
				com.google.protobuf.ExtensionRegistryLite extensionRegistry)
				throws java.io.IOException {
			return newBuilder().mergeFrom(input, extensionRegistry)
					.buildParsed();
		}

		public static br.ufs.dcomp.gpes.peticwizard.presentation.MobiPETIC.DadosIniciais.Usuario parseDelimitedFrom(
				java.io.InputStream input) throws java.io.IOException {
			Builder builder = newBuilder();
			if (builder.mergeDelimitedFrom(input)) {
				return builder.buildParsed();
			} else {
				return null;
			}
		}

		public static br.ufs.dcomp.gpes.peticwizard.presentation.MobiPETIC.DadosIniciais.Usuario parseDelimitedFrom(
				java.io.InputStream input,
				com.google.protobuf.ExtensionRegistryLite extensionRegistry)
				throws java.io.IOException {
			Builder builder = newBuilder();
			if (builder.mergeDelimitedFrom(input, extensionRegistry)) {
				return builder.buildParsed();
			} else {
				return null;
			}
		}

		public static br.ufs.dcomp.gpes.peticwizard.presentation.MobiPETIC.DadosIniciais.Usuario parseFrom(
				com.google.protobuf.CodedInputStream input)
				throws java.io.IOException {
			return newBuilder().mergeFrom(input).buildParsed();
		}

		public static br.ufs.dcomp.gpes.peticwizard.presentation.MobiPETIC.DadosIniciais.Usuario parseFrom(
				com.google.protobuf.CodedInputStream input,
				com.google.protobuf.ExtensionRegistryLite extensionRegistry)
				throws java.io.IOException {
			return newBuilder().mergeFrom(input, extensionRegistry)
					.buildParsed();
		}

		public static Builder newBuilder() {
			return Builder.create();
		}

		public Builder newBuilderForType() {
			return newBuilder();
		}

		public static Builder newBuilder(
				br.ufs.dcomp.gpes.peticwizard.presentation.MobiPETIC.DadosIniciais.Usuario prototype) {
			return newBuilder().mergeFrom(prototype);
		}

		public Builder toBuilder() {
			return newBuilder(this);
		}

		@java.lang.Override
		protected Builder newBuilderForType(
				com.google.protobuf.GeneratedMessage.BuilderParent parent) {
			Builder builder = new Builder(parent);
			return builder;
		}

		public static final class Builder extends
				com.google.protobuf.GeneratedMessage.Builder<Builder>
				implements
				br.ufs.dcomp.gpes.peticwizard.presentation.MobiPETIC.DadosIniciais.UsuarioOrBuilder {
			public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
				return br.ufs.dcomp.gpes.peticwizard.presentation.MobiPETIC.DadosIniciais.internal_static_MobiPETIC_Usuario_descriptor;
			}

			@Override
			protected com.google.protobuf.GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
				return br.ufs.dcomp.gpes.peticwizard.presentation.MobiPETIC.DadosIniciais.internal_static_MobiPETIC_Usuario_fieldAccessorTable;
			}

			// Construct using
			// br.ufs.dcomp.gpes.peticwizard.presentation.MobiPETIC.DadosIniciais.Usuario.newBuilder()
			private Builder() {
				maybeForceBuilderInitialization();
			}

			private Builder(BuilderParent parent) {
				super(parent);
				maybeForceBuilderInitialization();
			}

			private void maybeForceBuilderInitialization() {
				if (com.google.protobuf.GeneratedMessage.alwaysUseFieldBuilders) {
				}
			}

			private static Builder create() {
				return new Builder();
			}

			@Override
			public Builder clear() {
				super.clear();
				id_ = 0;
				bitField0_ = (bitField0_ & ~0x00000001);
				empresaId_ = 0;
				bitField0_ = (bitField0_ & ~0x00000002);
				empresaLogo_ = com.google.protobuf.ByteString.EMPTY;
				bitField0_ = (bitField0_ & ~0x00000004);
				return this;
			}

			@Override
			public Builder clone() {
				return create().mergeFrom(buildPartial());
			}

			@Override
			public com.google.protobuf.Descriptors.Descriptor getDescriptorForType() {
				return br.ufs.dcomp.gpes.peticwizard.presentation.MobiPETIC.DadosIniciais.Usuario
						.getDescriptor();
			}

			public br.ufs.dcomp.gpes.peticwizard.presentation.MobiPETIC.DadosIniciais.Usuario getDefaultInstanceForType() {
				return br.ufs.dcomp.gpes.peticwizard.presentation.MobiPETIC.DadosIniciais.Usuario
						.getDefaultInstance();
			}

			public br.ufs.dcomp.gpes.peticwizard.presentation.MobiPETIC.DadosIniciais.Usuario build() {
				br.ufs.dcomp.gpes.peticwizard.presentation.MobiPETIC.DadosIniciais.Usuario result = buildPartial();
				if (!result.isInitialized()) {
					throw newUninitializedMessageException(result);
				}
				return result;
			}

			private br.ufs.dcomp.gpes.peticwizard.presentation.MobiPETIC.DadosIniciais.Usuario buildParsed()
					throws com.google.protobuf.InvalidProtocolBufferException {
				br.ufs.dcomp.gpes.peticwizard.presentation.MobiPETIC.DadosIniciais.Usuario result = buildPartial();
				if (!result.isInitialized()) {
					throw newUninitializedMessageException(result)
							.asInvalidProtocolBufferException();
				}
				return result;
			}

			public br.ufs.dcomp.gpes.peticwizard.presentation.MobiPETIC.DadosIniciais.Usuario buildPartial() {
				br.ufs.dcomp.gpes.peticwizard.presentation.MobiPETIC.DadosIniciais.Usuario result = new br.ufs.dcomp.gpes.peticwizard.presentation.MobiPETIC.DadosIniciais.Usuario(
						this);
				int from_bitField0_ = bitField0_;
				int to_bitField0_ = 0;
				if (((from_bitField0_ & 0x00000001) == 0x00000001)) {
					to_bitField0_ |= 0x00000001;
				}
				result.id_ = id_;
				if (((from_bitField0_ & 0x00000002) == 0x00000002)) {
					to_bitField0_ |= 0x00000002;
				}
				result.empresaId_ = empresaId_;
				if (((from_bitField0_ & 0x00000004) == 0x00000004)) {
					to_bitField0_ |= 0x00000004;
				}
				result.empresaLogo_ = empresaLogo_;
				result.bitField0_ = to_bitField0_;
				onBuilt();
				return result;
			}

			@Override
			public Builder mergeFrom(com.google.protobuf.Message other) {
				if (other instanceof br.ufs.dcomp.gpes.peticwizard.presentation.MobiPETIC.DadosIniciais.Usuario) {
					return mergeFrom((br.ufs.dcomp.gpes.peticwizard.presentation.MobiPETIC.DadosIniciais.Usuario) other);
				} else {
					super.mergeFrom(other);
					return this;
				}
			}

			public Builder mergeFrom(
					br.ufs.dcomp.gpes.peticwizard.presentation.MobiPETIC.DadosIniciais.Usuario other) {
				if (other == br.ufs.dcomp.gpes.peticwizard.presentation.MobiPETIC.DadosIniciais.Usuario
						.getDefaultInstance())
					return this;
				if (other.hasId()) {
					setId(other.getId());
				}
				if (other.hasEmpresaId()) {
					setEmpresaId(other.getEmpresaId());
				}
				if (other.hasEmpresaLogo()) {
					setEmpresaLogo(other.getEmpresaLogo());
				}
				this.mergeUnknownFields(other.getUnknownFields());
				return this;
			}

			@Override
			public final boolean isInitialized() {
				if (!hasId()) {

					return false;
				}
				if (!hasEmpresaId()) {

					return false;
				}
				return true;
			}

			@Override
			public Builder mergeFrom(
					com.google.protobuf.CodedInputStream input,
					com.google.protobuf.ExtensionRegistryLite extensionRegistry)
					throws java.io.IOException {
				com.google.protobuf.UnknownFieldSet.Builder unknownFields = com.google.protobuf.UnknownFieldSet
						.newBuilder(this.getUnknownFields());
				while (true) {
					int tag = input.readTag();
					switch (tag) {
					case 0:
						this.setUnknownFields(unknownFields.build());
						onChanged();
						return this;
					default: {
						if (!parseUnknownField(input, unknownFields,
								extensionRegistry, tag)) {
							this.setUnknownFields(unknownFields.build());
							onChanged();
							return this;
						}
						break;
					}
					case 8: {
						bitField0_ |= 0x00000001;
						id_ = input.readInt32();
						break;
					}
					case 16: {
						bitField0_ |= 0x00000002;
						empresaId_ = input.readInt32();
						break;
					}
					case 26: {
						bitField0_ |= 0x00000004;
						empresaLogo_ = input.readBytes();
						break;
					}
					}
				}
			}

			private int bitField0_;

			// required int32 id = 1;
			private int id_;

			public boolean hasId() {
				return ((bitField0_ & 0x00000001) == 0x00000001);
			}

			public int getId() {
				return id_;
			}

			public Builder setId(int value) {
				bitField0_ |= 0x00000001;
				id_ = value;
				onChanged();
				return this;
			}

			public Builder clearId() {
				bitField0_ = (bitField0_ & ~0x00000001);
				id_ = 0;
				onChanged();
				return this;
			}

			// required int32 empresa_id = 2;
			private int empresaId_;

			public boolean hasEmpresaId() {
				return ((bitField0_ & 0x00000002) == 0x00000002);
			}

			public int getEmpresaId() {
				return empresaId_;
			}

			public Builder setEmpresaId(int value) {
				bitField0_ |= 0x00000002;
				empresaId_ = value;
				onChanged();
				return this;
			}

			public Builder clearEmpresaId() {
				bitField0_ = (bitField0_ & ~0x00000002);
				empresaId_ = 0;
				onChanged();
				return this;
			}

			// optional bytes empresa_logo = 3;
			private com.google.protobuf.ByteString empresaLogo_ = com.google.protobuf.ByteString.EMPTY;

			public boolean hasEmpresaLogo() {
				return ((bitField0_ & 0x00000004) == 0x00000004);
			}

			public com.google.protobuf.ByteString getEmpresaLogo() {
				return empresaLogo_;
			}

			public Builder setEmpresaLogo(com.google.protobuf.ByteString value) {
				if (value == null) {
					throw new NullPointerException();
				}
				bitField0_ |= 0x00000004;
				empresaLogo_ = value;
				onChanged();
				return this;
			}

			public Builder clearEmpresaLogo() {
				bitField0_ = (bitField0_ & ~0x00000004);
				empresaLogo_ = getDefaultInstance().getEmpresaLogo();
				onChanged();
				return this;
			}

			// @@protoc_insertion_point(builder_scope:MobiPETIC.Usuario)
		}

		static {
			defaultInstance = new Usuario(true);
			defaultInstance.initFields();
		}

		// @@protoc_insertion_point(class_scope:MobiPETIC.Usuario)
	}

	private static com.google.protobuf.Descriptors.Descriptor internal_static_MobiPETIC_Usuario_descriptor;
	private static com.google.protobuf.GeneratedMessage.FieldAccessorTable internal_static_MobiPETIC_Usuario_fieldAccessorTable;

	public static com.google.protobuf.Descriptors.FileDescriptor getDescriptor() {
		return descriptor;
	}

	private static com.google.protobuf.Descriptors.FileDescriptor descriptor;
	static {
		java.lang.String[] descriptorData = { "\n\023dadosiniciais.proto\022\tMobiPETIC\"?\n\007Usua"
				+ "rio\022\n\n\002id\030\001 \002(\005\022\022\n\nempresa_id\030\002 \002(\005\022\024\n\014e"
				+ "mpresa_logo\030\003 \001(\014BE\n4br.ufs.dcomp.gpes.p"
				+ "eticwizard.presentation.MobiPETICB\rDados" + "Iniciais" };
		com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner = new com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner() {
			public com.google.protobuf.ExtensionRegistry assignDescriptors(
					com.google.protobuf.Descriptors.FileDescriptor root) {
				descriptor = root;
				internal_static_MobiPETIC_Usuario_descriptor = getDescriptor()
						.getMessageTypes().get(0);
				internal_static_MobiPETIC_Usuario_fieldAccessorTable = new com.google.protobuf.GeneratedMessage.FieldAccessorTable(
						internal_static_MobiPETIC_Usuario_descriptor,
						new java.lang.String[] { "Id", "EmpresaId",
								"EmpresaLogo", },
						br.ufs.dcomp.gpes.peticwizard.presentation.MobiPETIC.DadosIniciais.Usuario.class,
						br.ufs.dcomp.gpes.peticwizard.presentation.MobiPETIC.DadosIniciais.Usuario.Builder.class);
				return null;
			}
		};
		com.google.protobuf.Descriptors.FileDescriptor
				.internalBuildGeneratedFileFrom(
						descriptorData,
						new com.google.protobuf.Descriptors.FileDescriptor[] {},
						assigner);
	}

	// @@protoc_insertion_point(outer_class_scope)
}
